package com.atguigu.blo.service;

import com.atguigu.blo.entity.Article;
import com.atguigu.blo.repository.ArticleRepository;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final Parser markdownParser;
    private final HtmlRenderer htmlRenderer;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, List.of(
                com.vladsch.flexmark.ext.tables.TablesExtension.create(),
                com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension.create(),
                com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension.create(),
                com.vladsch.flexmark.ext.emoji.EmojiExtension.create(),
                com.vladsch.flexmark.ext.autolink.AutolinkExtension.create()
        ));
        this.markdownParser = Parser.builder(options).build();
        this.htmlRenderer = HtmlRenderer.builder(options).build();
    }

    @PostConstruct
    public void initSeedData() {
        if (articleRepository.count() > 0) return;

        articleRepository.save(new Article(
            "Spring Boot 微服务实战指南",
            """
## 什么是微服务架构？

微服务架构是一种将单个应用程序开发为一组小型服务的方法，每个服务运行在自己的进程中，并通过轻量级机制进行通信。

> 微服务的核心思想是围绕业务能力构建服务，而不是围绕技术层。

## Spring Boot 的优势

Spring Boot 让创建独立的、生产级别的 Spring 应用程序变得非常容易。

### 自动配置

Spring Boot 会根据项目依赖自动配置应用程序，大大减少了手动配置的工作量。

### 嵌入式服务器

无需部署 WAR 文件，内置 Tomcat、Jetty 等服务器，可以直接运行。

## 服务注册与发现

```java
@SpringBootApplication
@EnableEurekaServer
public class RegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegistryApplication.class, args);
    }
}
```

通过以上配置，一个简单的服务注册中心就搭建完成了。""",
            "深入探讨 Spring Boot 在微服务架构中的最佳实践，从服务注册到网关配置的完整指南。",
            "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=800",
            "技术", "Spring Boot, 微服务, Java"
        ));

        articleRepository.save(new Article(
            "React 18 新特性深度解析",
            """
## React 18 来了！

React 18 引入了许多令人兴奋的新特性，包括并发渲染、自动批处理和服务端 Suspense。

## 并发渲染

并发模式是 React 18 最重要的更新。它允许 React 同时准备多个版本的 UI，从而实现更流畅的用户体验。

> 并发渲染让 React 可以在后台准备新的 UI，而不阻塞主线程。

### useTransition

新的 useTransition hook 允许你将某些状态更新标记为"过渡"：

```javascript
const [isPending, startTransition] = useTransition();
startTransition(() => {
    setSearchQuery(input);
});
```

## 自动批处理

在 React 18 中，所有状态更新都会自动批处理，无论是在事件处理器、setTimeout 还是 Promise 中。""",
            "全面了解 React 18 带来的并发特性、自动批处理和 Suspense 改进。",
            "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800",
            "技术", "React, JavaScript, 前端"
        ));

        articleRepository.save(new Article(
            "周末徒步记：城市边缘的山野",
            """
## 说走就走的小旅行

周末早上七点，被窗外的鸟鸣叫醒。突然很想出去走走，于是背起包就出发了。

![山林小径](https://images.unsplash.com/photo-1551632811-561732d1e306?w=800)

## 山间的惊喜

沿着蜿蜒的小路走了大约两小时，眼前豁然开朗。一片开阔的草地，远处是层层叠叠的山峦。

> 城市里的我们总是被各种 deadline 推着走，但在山里，时间是用脚步来丈量的。

### 途中趣事

- 遇到了一只超级友好的金毛，它的主人说它每周都来
- 发现了一条隐藏的小溪，水质清澈得可以看见水底的石头
- 山顶有个大叔在拉手风琴，特别有意境

## 一点感悟

有时候，我们需要从代码的世界里抽离出来。大自然是最好的调试器——它帮你清理缓存，释放内存，然后重新启动。""",
            "一个程序员周末逃离城市、回归山野的徒步记录。",
            "https://images.unsplash.com/photo-1551632811-561732d1e306?w=800",
            "生活", "周末, 徒步, 户外, 生活感悟"
        ));

        articleRepository.save(new Article(
            "读书笔记：《人类简史》",
            """
## 为什么读这本书

一直听说这本书的大名，终于在这个月读完了。尤瓦尔·赫拉利的视野确实宏大。

![书籍照片](https://images.unsplash.com/photo-1512820790803-83ca734da794?w=800)

## 三个关键革命

赫拉利将人类历史分为三个重要革命：

### 1. 认知革命（约7万年前）

人类获得了**虚构故事**的能力。这听起来简单，但意义重大——它让大规模陌生人协作成为可能。

### 2. 农业革命（约1.2万年前）

人类从狩猎采集转向农耕。赫拉利有一个反直觉的观点：

> 农业革命是史上最大的骗局。小麦驯化了人类，而不是人类驯化了小麦。

### 3. 科学革命（约500年前）

人类承认自己的无知，开始用科学方法探索世界。

## 我的思考

作为程序员读这本书，有一个有趣的视角：人类社会运行在"虚构的故事"之上——国家、货币、公司、法律，这些不都是人类集体想象的产物吗？就像代码一样，它们之所以"真实"，是因为足够多的人相信并遵循它们。""",
            "尤瓦尔·赫拉利的《人类简史》读书笔记与思考，从认知革命到科学革命。",
            "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=800",
            "阅读", "读书笔记, 人类简史, 历史, 思考"
        ));

        articleRepository.save(new Article(
            "我的工作台进化史",
            """
## 第一代：学生时代的折叠桌

大学时期，一张 60cm 宽的折叠桌就是我的全部天地。笔记本散热器嗡嗡作响，旁边的台灯还是舍友毕业留下的。

## 第二代：租房时期的宜家桌

毕业后租房，买了一张最便宜的宜家桌子。桌面慢慢被各种设备占据：显示器、机械键盘、耳机架、咖啡杯...空间永远不够用。

> 工欲善其事，必先利其器。一张好桌子，是对自己每天坐八小时的基本尊重。

## 第三代：现在的理想桌面

搬了新家后，终于可以认真布置了：

- **电动升降桌** — 站坐交替，腰终于不疼了
- **双显示器** — 一个竖屏写代码，一个横屏看文档
- **机械键盘** — 青轴，打字的时候噼里啪啦很解压
- **一盏暖光台灯** — 晚上不刺眼，氛围感拉满
- **绿植** — 桌角放了一盆小龟背竹

## 桌面之外

其实最重要的不是设备，而是这个空间带给你的**专注感**。每当坐到这里，大脑就自动切换到工作模式。这是一种仪式感。""",
            "从学生时代的折叠桌到理想桌面，一个程序员的桌面进化史。",
            "https://images.unsplash.com/photo-1593062096033-9a26b09da705?w=800",
            "生活", "桌面, 数码, 效率, 生活"
        ));

        articleRepository.save(new Article(
            "Docker 容器化部署实战",
            """
## 为什么需要容器化？

容器化技术解决了"在我机器上能跑"的问题。通过将应用及其依赖打包在一起，确保在任何环境中都能一致运行。

## Docker 基础

Docker 是目前最流行的容器化平台。

### 核心概念

- **镜像（Image）**：容器的模板，包含运行应用所需的一切
- **容器（Container）**：镜像的运行实例
- **Dockerfile**：定义镜像构建过程的文本文件

## 编写 Dockerfile

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

使用多阶段构建可以进一步减小镜像体积。""",
            "从零开始学习 Docker，掌握容器化技术，实现应用的快速部署和运维。",
            "https://images.unsplash.com/photo-1605745341112-85968b19335b?w=800",
            "技术", "Docker, DevOps, 运维"
        ));

        articleRepository.save(new Article(
            "读书笔记：《代码整洁之道》",
            """
## 什么是整洁的代码？

读完 Uncle Bob 的这本经典，对"整洁代码"有了更深的理解。

> 写代码就像写文章。先写草稿，再反复修改，直到每个句子都准确传达意图。

## 核心原则

### 有意义的命名

变量名应该回答：**它为什么存在、它做什么事、它应该怎么用**。

### 函数应该短小

一个好的函数应该只做一件事，做好这件事，并且只做这件事。

### 注释是失败的补偿

与其写注释解释复杂的代码，不如把代码写得更清晰。

## 实践感受

读完这本书后，我开始在写代码时更多地思考"半年后的我能看懂这段代码吗"。好的代码本身就是最好的文档。

整洁的代码不是一次写成的，而是一次次小步重构的结果。""",
            "Uncle Bob 经典著作《代码整洁之道》的读后感与实践体会。",
            "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=800",
            "阅读", "读书笔记, 代码整洁之道, 编程, 思考"
        ));

        articleRepository.save(new Article(
            "Go 语言并发编程深入",
            """
## Go 的并发模型

Go 语言从设计之初就将并发作为一等公民。goroutine 和 channel 提供了一种优雅的并发编程方式。

## Goroutine

Goroutine 是 Go 的轻量级线程，创建成本极低：

```go
go func() {
    fmt.Println("Hello from goroutine!")
}()
```

## Channel

Channel 是 goroutine 之间通信的管道。

> 不要通过共享内存来通信，而要通过通信来共享内存。

这是 Go 并发哲学的核心。""",
            "探索 Go 语言的 goroutine 和 channel，掌握高效并发编程的核心技巧。",
            "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=800",
            "技术", "Go, 并发, 后端"
        ));

        articleRepository.save(new Article(
            "小咖啡馆探店记",
            """
## 藏在巷子里的惊喜

周六下午，本来只是去修手机，结果在商场后面的小巷里发现了一家超棒的小咖啡馆。

## 环境

店面不大，大概只有七八个座位。但细节满分：

- 墙上是手绘的咖啡地图
- 每张桌子上都有一本不同的书
- 音乐是轻柔的爵士钢琴
- 阳光透过百叶窗洒进来

> 好的咖啡馆不是喝咖啡的地方，是和时间做朋友的地方。

## 咖啡

点了一杯手冲耶加雪菲。老板是个话不多的中年人，但冲咖啡的时候眼神特别专注。酸度适中，有淡淡的花果香气。

这种偶然发现的小店，比那些网红打卡地舒服多了。下次还会来。""",
            "一个偶然发现的宝藏咖啡馆，藏在城市巷子里的温暖角落。",
            "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=800",
            "生活", "咖啡, 探店, 周末, 生活"
        ));

        articleRepository.save(new Article(
            "CSS Grid 现代布局完全指南",
            """
## 为什么选择 Grid？

CSS Grid 是二维布局系统，可以同时处理行和列，相比 Flexbox 更适合创建复杂的页面布局。

## 基础概念

Grid 容器和 Grid 项目是理解 Grid 布局的关键。

```css
.container {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
}
```

## 响应式布局

结合媒体查询和 auto-fit/auto-fill，可以轻松创建响应式布局。""",
            "掌握 CSS Grid 布局系统，轻松创建复杂的响应式网页布局。",
            "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=800",
            "技术", "CSS, 前端, 响应式"
        ));
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Article> getArticlesByCategory(String category) {
        return articleRepository.findByCategoryOrderByCreatedAtDesc(category);
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public List<String> getAllCategories() {
        return articleRepository.findDistinctCategories();
    }

    public Article saveArticle(Article article) {
        if (article.getExcerpt() == null || article.getExcerpt().isBlank()) {
            article.setExcerpt(article.getContent().length() > 150
                    ? article.getContent().substring(0, 150).replaceAll("[#*>`\\n\\r]", " ").trim() + "..."
                    : article.getContent().replaceAll("[#*>`\\n\\r]", " ").trim());
        }
        return articleRepository.save(article);
    }

    public void incrementViews(Long id) {
        articleRepository.findById(id).ifPresent(article -> {
            article.setViews(article.getViews() + 1);
            articleRepository.save(article);
        });
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public String renderMarkdown(String markdown) {
        return htmlRenderer.render(markdownParser.parse(markdown));
    }

    public List<Article> searchArticles(String keyword) {
        return articleRepository.findByTitleContainingOrContentContainingOrTagsContainingOrderByCreatedAtDesc(
                keyword, keyword, keyword);
    }
}
