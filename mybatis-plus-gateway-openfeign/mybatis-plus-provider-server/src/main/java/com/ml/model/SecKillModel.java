package com.ml.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * SecKillModel
 *
 * @author dml
 * @date 2021/10/29 15:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "tb_seckill")
public class SecKillModel {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String goodId;

    private Date createTime;

    private Integer status;

}
