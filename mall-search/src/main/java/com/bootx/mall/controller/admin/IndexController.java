
package com.bootx.mall.controller.admin;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bootx.mall.entity.Business;
import com.bootx.mall.entity.BusinessCash;
import com.bootx.mall.entity.CategoryApplication;
import com.bootx.mall.entity.DistributionCash;
import com.bootx.mall.entity.Distributor;
import com.bootx.mall.entity.Order;
import com.bootx.mall.entity.Product;
import com.bootx.mall.entity.ProductCategory;
import com.bootx.mall.entity.Store;
import com.bootx.mall.service.BusinessCashService;
import com.bootx.mall.service.BusinessService;
import com.bootx.mall.service.CategoryApplicationService;
import com.bootx.mall.service.DistributionCashService;
import com.bootx.mall.service.MemberService;
import com.bootx.mall.service.OrderService;
import com.bootx.mall.service.ProductService;
import com.bootx.mall.service.StoreService;

/**
 * Controller - 首页
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("adminIndexController")
@RequestMapping("/admin/index")
public class IndexController extends BaseController {

	@Value("${system.name}")
	private String systemName;
	@Value("${system.version}")
	private String systemVersion;
	@Value("${system.description}")
	private String systemDescription;

	@Inject
	private ServletContext servletContext;
	@Inject
	private StoreService storeService;
	@Inject
	private ProductService productService;
	@Inject
	private BusinessCashService businessCashService;
	@Inject
	private CategoryApplicationService categoryApplicationService;
	@Inject
	private DistributionCashService distributionCashService;
	@Inject
	private MemberService memberService;
	@Inject
	private BusinessService businessService;
	@Inject
	private OrderService orderService;

	/**
	 * 首页
	 */
	@GetMapping
	public String index(ModelMap model) {
		Date now = new Date();
		Date todayMinimumDate = DateUtils.truncate(now, Calendar.DAY_OF_MONTH);

		model.addAttribute("systemName", systemName);
		model.addAttribute("systemVersion", systemVersion);
		model.addAttribute("systemDescription", systemDescription);
		model.addAttribute("javaVersion", System.getProperty("java.version"));
		model.addAttribute("javaHome", System.getProperty("java.home"));
		model.addAttribute("osName", System.getProperty("os.name"));
		model.addAttribute("osArch", System.getProperty("os.arch"));
		model.addAttribute("serverInfo", servletContext.getServerInfo());
		model.addAttribute("servletVersion", servletContext.getMajorVersion() + "." + servletContext.getMinorVersion());
		model.addAttribute("storeReviewCount", storeService.count(null, Store.Status.PENDING, null, true));
		model.addAttribute("businessCashReviewCount", businessCashService.count((Business) null, BusinessCash.Status.PENDING, null, null));
		model.addAttribute("categoryApplicationReviewCount", categoryApplicationService.count(CategoryApplication.Status.PENDING, (Store) null, (ProductCategory) null));
		model.addAttribute("weekSalesList", productService.findList(Product.RankingType.WEEK_SALES, null, 2));
		model.addAttribute("monthSalesList", productService.findList(Product.RankingType.MONTH_SALES, null, 2));
		model.addAttribute("distributionCashReviewCount", distributionCashService.count(DistributionCash.Status.PENDING, null, null, null, (Distributor) null));
		model.addAttribute("todayAddedMemberCount", memberService.count(todayMinimumDate, null));
		model.addAttribute("todayAddedPlatformCommission", orderService.grantedCommissionTotalAmount(null, Order.CommissionType.PLATFORM, todayMinimumDate, null, Order.Status.COMPLETED));
		model.addAttribute("todayAddedDistributionCommission", orderService.grantedCommissionTotalAmount(null, Order.CommissionType.DISTRIBUTION, todayMinimumDate, null, Order.Status.COMPLETED));
		model.addAttribute("yesterdayAddedMemberCount", memberService.count(DateUtils.addDays(todayMinimumDate, -1), DateUtils.addMilliseconds(todayMinimumDate, -1)));
		model.addAttribute("currentMonthAddedMemberCount", memberService.count(DateUtils.truncate(now, Calendar.MONTH), null));
		model.addAttribute("memberTotal", memberService.count());
		model.addAttribute("todayAddedBusinessCount", businessService.count(todayMinimumDate, null));
		model.addAttribute("yesterdayAddedBusinessCount", businessService.count(DateUtils.addDays(todayMinimumDate, -1), DateUtils.addMilliseconds(todayMinimumDate, -1)));
		model.addAttribute("currentMonthAddedBusinessCount", businessService.count(DateUtils.truncate(now, Calendar.MONTH), null));
		model.addAttribute("businessTotal", businessService.count());
		return "admin/index";
	}

}