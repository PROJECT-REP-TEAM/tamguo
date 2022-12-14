package com.tamguo.modules.tiku.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.tamguo.common.utils.ExceptionSupport;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.condition.ChapterCondition;
import com.tamguo.modules.tiku.service.IChapterService;

@Controller
@RequestMapping(path="tiku/chapter")
public class  {
	
	private final String ADD_CHAPTER_PAGE = "modules/tiku/chapter/add";
	private final String UPDATE_CHAPTER_PAGE = "modules/tiku/chapter/update";
	
	@Autowired
	private IChapterService iChapterService;
	
	@RequestMapping(path="add",method=RequestMethod.GET)
	public ModelAndView add(String parentChapterId , ModelAndView model) {
	 	ChapterEntity parentChapter = iChapterService.selectById(parentChapterId);
	 	model.addObject("parentChapter", parentChapter);
		model.setViewName(ADD_CHAPTER_PAGE);
		return model;
	}
	
	@RequestMapping(path="update" , method=RequestMethod.GET)
	public ModelAndView update(String id , ModelAndView model) {
		ChapterEntity chapter = iChapterService.selectById(id);
		ChapterEntity parentChapter = iChapterService.selectById(chapter.getParentCode());
		model.addObject("chapter", chapter);
		model.addObject("parentChapter", parentChapter);
		model.setViewName(UPDATE_CHAPTER_PAGE);
		return model;
	}

	@RequestMapping(path="listData",method=RequestMethod.POST)
	@ResponseBody
	public List<ChapterEntity> listData(ChapterCondition condition) {
		return iChapterService.listData(condition);
	}
	
	@RequestMapping(path="treeData")
	@ResponseBody
	public JSONArray treeData(String courseId , String excludeId) {
		return iChapterService.treeData(courseId , excludeId);
	}
	
	@RequestMapping(path="save")
	@ResponseBody
	public Result save(ChapterEntity chapter) {
		try {
			iChapterService.save(chapter);
			return Result.result(0, null, "?????????"+chapter.getName()+"??????????????????");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("????????????", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="update")
	@ResponseBody
	public Result update(ChapterEntity chapter) {
		try {
			iChapterService.update(chapter);
			return Result.result(0, null, "?????????"+chapter.getName()+"??????????????????");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("????????????", this.getClass(), e);
		}
	}
	
	@RequestMapping(path="delete" , method=RequestMethod.POST)
	@ResponseBody
	public Result delete(String id) {
		try {
			iChapterService.delete(id);
			return Result.result(0, null, "?????????????????????");
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("????????????", this.getClass(), e);
		}
	}
}
