
package com.bootx.mall.event;

import com.bootx.mall.entity.Cart;

/**
 * Event - 清空购物车SKU
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public class CartClearedEvent extends CartEvent {

	private static final long serialVersionUID = -5881246837387897341L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param cart
	 *            购物车
	 */
	public CartClearedEvent(Object source, Cart cart) {
		super(source, cart);
	}

}