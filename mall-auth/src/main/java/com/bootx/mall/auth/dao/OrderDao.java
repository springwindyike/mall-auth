
package com.bootx.mall.auth.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.entity.Member;
import com.bootx.mall.auth.entity.Product;
import com.bootx.mall.auth.entity.Store;

/**
 * Dao - 订单
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface OrderDao extends BaseDao<com.bootx.mall.auth.entity.Order, Long> {

	/**
	 * 查找订单
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param store
	 *            店铺
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isUseCouponCode
	 *            是否已使用优惠码
	 * @param isExchangePoint
	 *            是否已兑换积分
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 订单
	 */
	List<com.bootx.mall.auth.entity.Order> findList(com.bootx.mall.auth.entity.Order.Type type, com.bootx.mall.auth.entity.Order.Status status, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, Integer count, List<Filter> filters,
                                                    List<Order> orders);

	/**
	 * 查找订单
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param store
	 *            店铺
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isUseCouponCode
	 *            是否已使用优惠码
	 * @param isExchangePoint
	 *            是否已兑换积分
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 订单
	 */
	List<com.bootx.mall.auth.entity.Order> findList(com.bootx.mall.auth.entity.Order.Type type, com.bootx.mall.auth.entity.Order.Status status, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, Integer first, Integer count,
                                                    List<Filter> filters, List<Order> orders);

	/**
	 * 查找订单分页
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param store
	 *            店铺
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isUseCouponCode
	 *            是否已使用优惠码
	 * @param isExchangePoint
	 *            是否已兑换积分
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<com.bootx.mall.auth.entity.Order> findPage(com.bootx.mall.auth.entity.Order.Type type, com.bootx.mall.auth.entity.Order.Status status, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable);

	/**
	 * 查询订单数量
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param store
	 *            店铺
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isUseCouponCode
	 *            是否已使用优惠码
	 * @param isExchangePoint
	 *            是否已兑换积分
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @return 订单数量
	 */
	Long count(com.bootx.mall.auth.entity.Order.Type type, com.bootx.mall.auth.entity.Order.Status status, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired);

	/**
	 * 查询订单创建数
	 * 
	 * @param store
	 *            店铺
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 订单创建数
	 */
	Long createOrderCount(Store store, Date beginDate, Date endDate);

	/**
	 * 查询订单完成数
	 * 
	 * @param store
	 *            店铺
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 订单完成数
	 */
	Long completeOrderCount(Store store, Date beginDate, Date endDate);

	/**
	 * 查询订单创建金额
	 * 
	 * @param store
	 *            店铺
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 订单创建金额
	 */
	BigDecimal createOrderAmount(Store store, Date beginDate, Date endDate);

	/**
	 * 查询订单完成金额
	 * 
	 * @param store
	 *            店铺
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 订单完成金额
	 */
	BigDecimal completeOrderAmount(Store store, Date beginDate, Date endDate);

	/**
	 * 查询已发放佣金总额
	 * 
	 * @param store
	 *            店铺
	 * @param commissionType
	 *            佣金类型
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param statuses
	 *            订单状态
	 * @return 已发放佣金总额
	 */
	BigDecimal grantedCommissionTotalAmount(Store store, com.bootx.mall.auth.entity.Order.CommissionType commissionType, Date beginDate, Date endDate, com.bootx.mall.auth.entity.Order.Status... statuses);

}