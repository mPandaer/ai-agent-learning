/* 可复用的练习组件：单选即时反馈，以及有限同步序列的需求演示。 */
document.querySelectorAll('[data-quiz]').forEach(quiz => {
  const feedback = quiz.querySelector('[role="status"]');
  quiz.querySelectorAll('button[data-correct]').forEach(button => {
    button.addEventListener('click', () => {
      feedback.textContent = button.dataset.correct === 'true'
        ? quiz.dataset.explanation : '再想一想：request(n) 增加的是允许发送的元素数量；终止信号不占这个数量。';
    });
  });
});

document.querySelectorAll('[data-demand-demo]').forEach(demo => {
  const log = demo.querySelector('pre');
  const state = demo.querySelector('[role="status"]');
  let subscribed, next, demand, completed;
  const show = () => {
    state.textContent = `订阅：${subscribed ? '已建立' : '未建立'} · 剩余需求：${demand} · 状态：${completed ? '已完成' : '等待操作'}`;
    demo.querySelector('[data-action="subscribe"]').disabled = subscribed;
    demo.querySelectorAll('[data-request]').forEach(b => { b.disabled = !subscribed || completed; });
  };
  const reset = () => {
    subscribed = false; next = 1; demand = 0; completed = false;
    log.textContent = '已组装 Flux.range(1, 3)\n'; show();
  };
  demo.querySelector('[data-action="reset"]').addEventListener('click', reset);
  demo.querySelector('[data-action="subscribe"]').addEventListener('click', () => {
    subscribed = true;
    log.textContent += 'subscribe → onSubscribe（暂不请求）\n'; show();
  });
  demo.querySelectorAll('[data-request]').forEach(button => {
    button.addEventListener('click', () => {
      const n = Number(button.dataset.request);
      demand += n; log.textContent += `request(${n})\n`;
      while (demand > 0 && next <= 3) {
        log.textContent += `onNext(${next++})\n`; demand--;
      }
      if (next > 3) { completed = true; log.textContent += 'onComplete()\n'; }
      show();
    });
  });
  reset();
});
