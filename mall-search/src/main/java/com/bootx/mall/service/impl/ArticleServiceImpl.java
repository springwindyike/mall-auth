
package com.bootx.mall.service.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bootx.mall.common.Filter;
import com.bootx.mall.common.Order;
import com.bootx.mall.common.Page;
import com.bootx.mall.common.Pageable;
import com.bootx.mall.dao.ArticleCategoryDao;
import com.bootx.mall.dao.ArticleDao;
import com.bootx.mall.dao.ArticleTagDao;
import com.bootx.mall.entity.Article;
import com.bootx.mall.entity.ArticleCategory;
import com.bootx.mall.entity.ArticleTag;
import com.bootx.mall.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * Service - 文章
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article, Long> implements ArticleService {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private CacheManager cacheManager;
	@Inject
	private ArticleDao articleDao;
	@Inject
	private ArticleCategoryDao articleCategoryDao;
	@Inject
	private ArticleTagDao articleTagDao;

	@Override
	@Transactional(readOnly = true)
	public List<Article> findList(ArticleCategory articleCategory, ArticleTag articleTag, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders) {
		return articleDao.findList(articleCategory, articleTag, isPublication, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "article", condition = "#useCache")
	public List<Article> findList(Long articleCategoryId, Long articleTagId, Boolean isPublication, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		ArticleCategory articleCategory = articleCategoryDao.find(articleCategoryId);
		if (articleCategoryId != null && articleCategory == null) {
			return Collections.emptyList();
		}
		ArticleTag articleTag = articleTagDao.find(articleTagId);
		if (articleTagId != null && articleTag == null) {
			return Collections.emptyList();
		}
		return articleDao.findList(articleCategory, articleTag, isPublication, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Article> findPage(ArticleCategory articleCategory, ArticleTag articleTag, Boolean isPublication, Pageable pageable) {
		return articleDao.findPage(articleCategory, articleTag, isPublication, pageable);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Article> search(String keyword, Pageable pageable) {
		if (StringUtils.isEmpty(keyword)) {
			return Page.emptyPage(pageable);
		}

		if (pageable == null) {
			pageable = new Pageable();
		}
		return Page.emptyPage(pageable);
	}

	@Override
	public long viewHits(Long id) {
		Assert.notNull(id, "[Assertion failed] - id is required; it must not be null");

		Ehcache cache = cacheManager.getEhcache(Article.HITS_CACHE_NAME);
		cache.acquireWriteLockOnKey(id);
		try {
			Element element = cache.get(id);
			Long hits;
			if (element != null) {
				hits = (Long) element.getObjectValue() + 1;
			} else {
				Article article = articleDao.find(id);
				if (article == null) {
					return 0L;
				}
				hits = article.getHits() + 1;
			}
			cache.put(new Element(id, hits));
			return hits;
		} finally {
			cache.releaseWriteLockOnKey(id);
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public Article save(Article article) {
		return super.save(article);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public Article update(Article article) {
		return super.update(article);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public Article update(Article article, String... ignoreProperties) {
		return super.update(article, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(Article article) {
		super.delete(article);
	}

}