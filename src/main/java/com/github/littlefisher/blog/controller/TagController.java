package com.github.littlefisher.blog.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.littlefisher.blog.controller.dto.TagDto;
import com.github.littlefisher.blog.controller.dto.base.BaseResponseDto;
import com.github.littlefisher.blog.service.TagService;
import com.github.littlefisher.mybatis.pagehelper.PageInfo;
import com.github.littlefisher.mybatis.pagehelper.PageParam;

/**
 * @author jinyanan
 * @since 2019/11/26 15:46
 */
@RestController
@RequestMapping("/blog/api/v1/tag")
@Validated
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 根据名称模糊匹配分页查询tag
     *
     * @param name tag名称
     * @param pageNum 页码
     * @param size 每页数量
     * @return tag列表
     */
    @GetMapping
    public BaseResponseDto<PageInfo<TagDto>> queryTagList(@RequestParam @NotNull @NotBlank String name,
        @RequestParam @NotNull Integer pageNum, @RequestParam @NotNull Integer size) {
        PageInfo<TagDto> pageInfo = tagService.queryTagList(name, PageParam.builder()
            .pageSize(size)
            .pageNum(pageNum)
            .build());
        return BaseResponseDto.success(pageInfo);
    }
}
