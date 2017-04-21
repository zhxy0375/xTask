package org.xtask.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 
 * @Description: SpringContext辅助类，用于在非spring管理的类中获取spring管理的bean
 */
@Service
public class SpringContextHelper implements ApplicationContextAware {

	public static ApplicationContext context;

	/**
	 * 根据名称获取SpringContext中的bean
	 * @param beanName
	 * @return
	 */
	public static Object getBeanByName(String beanName) {
		return context.getBean(beanName);
	}
	
	/**
	 * 根据类型获取SpringContext中的bean
	 * @param clazz
	 * @return
	 */
	public static <T> T getBeanByClass(Class<T> clazz) {
		return context.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		context = arg0;

	}

}