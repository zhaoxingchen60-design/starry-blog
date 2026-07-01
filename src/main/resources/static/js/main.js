/**
 * 星辰博客 - 主脚本
 */

document.addEventListener('DOMContentLoaded', () => {
  initStarfield();
  initNavbar();
  initMobileMenu();
  initBackToTop();
  initTypingEffect();
  initScrollReveal();
  initBlogFilter();
  initContactForm();
  initSmoothScroll();
});

/* ===== 星空背景 ===== */
function initStarfield() {
  const canvas = document.getElementById('starfield');
  if (!canvas) return;
  const ctx = canvas.getContext('2d');

  let stars = [];
  let animationId;

  function resize() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    initStars();
  }

  function initStars() {
    const count = Math.floor((canvas.width * canvas.height) / 3500);
    stars = [];
    for (let i = 0; i < count; i++) {
      stars.push({
        x: Math.random() * canvas.width,
        y: Math.random() * canvas.height,
        r: Math.random() * 1.5 + 0.3,
        opacity: Math.random(),
        speed: Math.random() * 0.008 + 0.002,
        twinkleSpeed: Math.random() * 0.02 + 0.005
      });
    }
  }

  function draw() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    stars.forEach(star => {
      star.opacity += star.twinkleSpeed;
      if (star.opacity >= 1 || star.opacity <= 0.1) {
        star.twinkleSpeed *= -1;
      }
      ctx.beginPath();
      ctx.arc(star.x, star.y, star.r, 0, Math.PI * 2);
      ctx.fillStyle = `rgba(180, 180, 255, ${star.opacity})`;
      ctx.fill();
    });

    // 连线
    for (let i = 0; i < stars.length; i++) {
      for (let j = i + 1; j < stars.length; j++) {
        const dx = stars[i].x - stars[j].x;
        const dy = stars[i].y - stars[j].y;
        const dist = Math.sqrt(dx * dx + dy * dy);
        if (dist < 120) {
          ctx.beginPath();
          ctx.moveTo(stars[i].x, stars[i].y);
          ctx.lineTo(stars[j].x, stars[j].y);
          ctx.strokeStyle = `rgba(124, 77, 255, ${0.06 * (1 - dist / 120)})`;
          ctx.lineWidth = 0.5;
          ctx.stroke();
        }
      }
    }
    animationId = requestAnimationFrame(draw);
  }

  resize();
  draw();
  window.addEventListener('resize', resize);
}

/* ===== 导航栏滚动效果 ===== */
function initNavbar() {
  const navbar = document.getElementById('navbar');
  if (!navbar) return;

  window.addEventListener('scroll', () => {
    if (window.scrollY > 50) {
      navbar.classList.add('scrolled');
    } else {
      navbar.classList.remove('scrolled');
    }
  });
}

/* ===== 移动端菜单 ===== */
function initMobileMenu() {
  const toggle = document.getElementById('menuToggle');
  const navLinks = document.getElementById('navLinks');
  if (!toggle || !navLinks) return;

  toggle.addEventListener('click', () => {
    navLinks.classList.toggle('open');
    const icon = toggle.querySelector('i');
    if (navLinks.classList.contains('open')) {
      icon.className = 'fa-solid fa-xmark';
    } else {
      icon.className = 'fa-solid fa-bars';
    }
  });

  // 点击菜单项关闭
  navLinks.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', () => {
      navLinks.classList.remove('open');
      toggle.querySelector('i').className = 'fa-solid fa-bars';
    });
  });

  // 点击空白处关闭
  document.addEventListener('click', (e) => {
    if (!navLinks.contains(e.target) && !toggle.contains(e.target)) {
      navLinks.classList.remove('open');
      toggle.querySelector('i').className = 'fa-solid fa-bars';
    }
  });
}

/* ===== 回到顶部 ===== */
function initBackToTop() {
  const btn = document.getElementById('backToTop');
  if (!btn) return;

  window.addEventListener('scroll', () => {
    if (window.scrollY > 400) {
      btn.classList.add('visible');
    } else {
      btn.classList.remove('visible');
    }
  });

  btn.addEventListener('click', () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  });
}

/* ===== 打字效果 ===== */
function initTypingEffect() {
  const el = document.getElementById('typing-text');
  if (!el) return;

  const phrases = [
    '分享技术干货，记录生活美好瞬间',
    '探索 Java、React、Python 前沿技术',
    '读一本好书，写一篇读书笔记',
    '周末徒步探店，发现城市角落的惊喜',
    '用代码书写世界，用文字温暖人心'
  ];
  let phraseIndex = 0;
  let charIndex = 0;
  let isDeleting = false;
  let typeSpeed = 80;

  function type() {
    const current = phrases[phraseIndex];
    if (isDeleting) {
      el.textContent = current.substring(0, charIndex - 1);
      charIndex--;
      typeSpeed = 30;
    } else {
      el.textContent = current.substring(0, charIndex + 1);
      charIndex++;
      typeSpeed = 80;
    }

    if (!isDeleting && charIndex === current.length) {
      typeSpeed = 2000; // 暂停
      isDeleting = true;
    } else if (isDeleting && charIndex === 0) {
      isDeleting = false;
      phraseIndex = (phraseIndex + 1) % phrases.length;
      typeSpeed = 500;
    }

    setTimeout(type, typeSpeed);
  }

  setTimeout(type, 500);
}

/* ===== 滚动显示动画 ===== */
function initScrollReveal() {
  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible');
      }
    });
  }, { threshold: 0.15, rootMargin: '0px 0px -50px 0px' });

  document.querySelectorAll('.post-card, .stat-card, .skill-card, .timeline-content, .contact-info-card, .cta-card').forEach(el => {
    el.classList.add('reveal');
    observer.observe(el);
  });
}

/* ===== 博客搜索 ===== */
function initBlogFilter() {
  const searchInput = document.getElementById('searchInput');
  if (!searchInput) return;

  let debounceTimer;
  searchInput.addEventListener('input', () => {
    clearTimeout(debounceTimer);
    debounceTimer = setTimeout(() => {
      const term = searchInput.value.trim();
      const url = term ? `/blog?search=${encodeURIComponent(term)}` : '/blog';
      window.location.href = url;
    }, 500);
  });

  // Enter键立即搜索
  searchInput.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') {
      clearTimeout(debounceTimer);
      const term = searchInput.value.trim();
      const url = term ? `/blog?search=${encodeURIComponent(term)}` : '/blog';
      window.location.href = url;
    }
  });
}

/* ===== 联系表单 ===== */
function initContactForm() {
  const form = document.getElementById('contactForm');
  if (!form) return;

  form.addEventListener('submit', (e) => {
    e.preventDefault();
    const btn = form.querySelector('button[type="submit"]');
    const originalHtml = btn.innerHTML;

    // 模拟发送
    btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> 发送中...';
    btn.disabled = true;

    setTimeout(() => {
      btn.innerHTML = '<i class="fa-solid fa-check"></i> 发送成功！';
      btn.style.background = 'linear-gradient(135deg, #43e97b, #38f9d7)';
      form.reset();

      setTimeout(() => {
        btn.innerHTML = originalHtml;
        btn.style.background = '';
        btn.disabled = false;
      }, 2500);
    }, 1500);
  });
}

/* ===== 平滑滚动(锚点) ===== */
function initSmoothScroll() {
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
      const target = document.querySelector(this.getAttribute('href'));
      if (target) {
        e.preventDefault();
        target.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    });
  });
}

/* ===== 代码高亮(文章详情页) ===== */
if (typeof hljs !== 'undefined') {
  hljs.highlightAll();
}
