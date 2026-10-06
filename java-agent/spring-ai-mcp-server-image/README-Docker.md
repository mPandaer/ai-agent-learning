# Docker 运行

在当前模块目录执行（需要 Docker Engine / Docker Desktop 和 Compose v2）：

```bash
docker compose up -d --build
docker compose logs -f image-mcp-server
```

默认 MCP 地址：`http://localhost:8082/mcp`。

## 外部配置映射

| 宿主机文件 | 容器内文件 |
| --- | --- |
| `src/main/resources/application.yml` | `/app/config/application.yml` |
| `src/main/resources/.env` | `/app/config/.env` |

Compose 将整个 `src/main/resources` 目录只读挂载到 `/app/config`，因此编辑器通过替换文件保存时也能正确同步。宿主机仍可正常编辑文件。启动前必须确保两个文件存在，并且容器用户有读取权限。

配置文件不进入 Docker 构建上下文或镜像。容器通过 `SPRING_CONFIG_LOCATION` 加载外部 YAML，通过 `SPRING_CONFIG_IMPORT` 加载外部 `.env`，覆盖现有 YAML 中的 classpath 导入。

`.env` 延续项目现有用法，以 Java properties 格式读取，例如：

```properties
IMAGE_MODEL=gpt-image-1
IMAGE_BASE_URL=https://api.openai.com/v1
IMAGE_API_KEY=替换为自己的密钥
```

不要写 `export`，也不要给值加 shell 风格的引号；包含反斜杠的值需要遵循 properties 转义规则。这不是 Compose 的 `env_file`，这些值不作为容器环境变量注入。

## 修改配置

修改宿主机上述文件后执行：

```bash
docker compose restart image-mcp-server
```

文件修改会同步到容器，但 Spring Boot 默认在启动时加载配置，**不支持这里的配置自动热更新**。重启即可读取新配置，无需重新构建镜像。

若修改 `server.port`，还需要同步修改 `compose.yml` 的端口映射，再执行 `docker compose up -d`。修改代码则需要重新执行 `docker compose up -d --build`。

## 网络注意事项

- 容器内的 `localhost` / `127.0.0.1` 指向容器自身，不是宿主机。
- MinIO 或 OpenAI 兼容接口若运行在宿主机，可在 YAML / `.env` 中使用 `http://host.docker.internal:端口`；Compose 已配置 Linux 的 host-gateway 映射。
- 当前配置使用 `server.local.com`，需要确认它在容器中可以解析并连通；宿主机的自定义 hosts 记录不会自动进入容器。
- `upload.images.base-url` 是返回给客户端的访问地址，远程客户端访问时应改成客户端可访问的宿主机 IP 或域名，而不是 `127.0.0.1`。
- 保管好 `.env` 和 YAML 内的凭证，不要提交真实密钥。服务默认映射宿主机的 8082 端口，公网部署前应增加访问控制。

## 当前构建验证

已删除 `pom.xml` 中额外指定的 `json-schema-validator:3.0.7`，改用 MCP SDK 传递依赖的 `3.0.0`，保留 Spring Boot 管理的 Jackson `3.1.5`。执行 `mvn -B -ntp clean verify` 成功，Maven Enforcer 依赖上界检查、编译和可执行 JAR 打包均通过；当前项目没有测试用例。本机没有安装 Docker CLI，尚未实测容器启动。

## 常用命令

```bash
# 检查 Compose 配置
docker compose config --quiet

# 停止并删除容器（不删除宿主机配置）
docker compose down
```

配置机制参考：Spring Boot 文档 `/spring-projects/spring-boot` 的 Externalized Configuration，以及 Docker Compose 文档 `/docker/compose` 的 bind mount。
