package com.tilitili.common.mapper;

import com.tilitili.common.entity.Subscription;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SubscriptionMapper {
	Long insertSubscription(Subscription subscription);
	void updateSubscription(Subscription subscription);
	int countSubscriptionByCondition (Subscription subscription);
	List<Subscription> listSubscriptionByCondition (Subscription subscription);

	@Select("select * from subscription where type = 1 and status != -1")
	List<Subscription> listUpSubscription();

	@Select("select * from subscription where type = 2 and status != -1")
	List<Subscription> listDynamicSubscription();

	@Select("select * from subscription where id = #{id}")
	Subscription getSubscriptionById(Long id);
}