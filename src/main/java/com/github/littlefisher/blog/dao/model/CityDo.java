package com.github.littlefisher.blog.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 *
 * city 实体<br>
 * <br>
 *
 * Created on 2019年11月25日
 * @author littlefisher
 * @version 2.1
 * @since v2.1
 */
@Table(name = "city")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@SuperBuilder
@NoArgsConstructor
public class CityDo implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * 城市编码
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    /**
     * 城市名称
     */
    private String name;

    /**
     * 省份编码
     */
    @Column(name = "province_code")
    private Integer provinceCode;
}