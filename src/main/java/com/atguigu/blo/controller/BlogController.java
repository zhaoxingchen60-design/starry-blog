package com.atguigu.blo.controller;

import com.atguigu.blo.entity.Article;
import com.atguigu.blo.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
public class BlogController {

    private final ArticleService articleService;

    public BlogController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Article> all = articleService.getAllArticles();
        List<Article> featured = all.stream().limit(3).toList();
        Map<String, List<Article>> categoryMap = new LinkedHashMap<>();
        for (Article a : all) {
            categoryMap.computeIfAbsent(a.getCategory(), k -> new ArrayList<>()).add(a);
        }
        model.addAttribute("featuredPosts", featured);
        model.addAttribute("categoryMap", categoryMap);
        model.addAttribute("postCount", all.size());
        model.addAttribute("currentYear", LocalDate.now().getYear());
        return "index";
    }

    @GetMapping("/blog")
    public String blog(@RequestParam(required = false) String category,
                       @RequestParam(required = false) String search,
                       Model model) {
        List<Article> posts;
        if (search != null && !search.isBlank()) {
            posts = articleService.searchArticles(search);
        } else if (category != null && !category.isBlank()) {
            posts = articleService.getArticlesByCategory(category);
        } else {
            posts = articleService.getAllArticles();
        }
        model.addAttribute("posts", posts);
        model.addAttribute("categories", articleService.getAllCategories());
        model.addAttribute("activeCategory", category);
        model.addAttribute("searchKeyword", search);
        model.addAttribute("currentYear", LocalDate.now().getYear());
        return "blog";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable Long id, Model model) {
        Optional<Article> post = articleService.getArticleById(id);
        if (post.isEmpty()) return "redirect:/blog";
        Article article = post.get();
        articleService.incrementViews(id);
        String rendered = articleService.renderMarkdown(article.getContent());
        model.addAttribute("post", article);
        model.addAttribute("renderedContent", rendered);
        List<Article> related = articleService.getAllArticles().stream()
                .filter(p -> !p.getId().equals(id))
                .limit(3).toList();
        model.addAttribute("relatedPosts", related);
        model.addAttribute("currentYear", LocalDate.now().getYear());
        return "post";
    }

    @GetMapping("/write")
    public String writePage(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("isEdit", false);
        model.addAttribute("currentYear", LocalDate.now().getYear());
        return "write";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        Optional<Article> article = articleService.getArticleById(id);
        if (article.isEmpty()) return "redirect:/blog";
        model.addAttribute("article", article.get());
        model.addAttribute("isEdit", true);
        model.addAttribute("currentYear", LocalDate.now().getYear());
        return "write";
    }

    @PostMapping("/save")
    public String saveArticle(@ModelAttribute Article article) {
        if (article.getCoverImage() == null || article.getCoverImage().isBlank()) {
            article.setCoverImage("https://images.unsplash.com/photo-1499750310107-5fef28a66643?w=800");
        }
        articleService.saveArticle(article);
        return "redirect:/blog";
    }

    @PostMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return "redirect:/blog";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("postCount", articleService.getAllArticles().size());
        model.addAttribute("currentYear", LocalDate.now().getYear());
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("currentYear", LocalDate.now().getYear());
        return "contact";
    }
}
