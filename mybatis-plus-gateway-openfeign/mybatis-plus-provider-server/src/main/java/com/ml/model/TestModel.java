package com.ml.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * TestModel
 *
 * @author Administrator
 * @date 2021/10/24 00:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "tb_test")
public class TestModel implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

}
