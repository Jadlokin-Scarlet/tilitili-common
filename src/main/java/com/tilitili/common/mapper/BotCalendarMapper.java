package com.tilitili.common.mapper;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import com.tilitili.common.entity.BotCalendar;

@Component
public interface BotCalendarMapper {
	Long insertBotCalendar(BotCalendar botCalendar);
	void updateBotCalendar(BotCalendar botCalendar);
	int countBotCalendarByCondition (BotCalendar botCalendar);
	List<BotCalendar> listBotCalendarByCondition (BotCalendar botCalendar);

	@Select("select * from bot_calendar where send_time < getdate() and status = 0")
	List<BotCalendar> listCanSendBotCalendar();
}