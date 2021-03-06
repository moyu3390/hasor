/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.db.dal.orm;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在类型上表示映射到的表
 * @version : 2020-10-31
 * @author 赵永春 (zyc@hasor.net)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /** 分类(对于mysql 是 db，对于 pg 是 schema，对于 oracle 是 owner) */
    public String category() default "";

    /** 表名，为空的话表示采用类名为表名 see: {@link #name()} */
    public String value() default "";

    /** 表名，为空的话表示采用类名为表名 see: {@link #value()} */
    public String name() default "";

    /** 列名是否不区分大小写，默认不区分 */
    public boolean caseInsensitive() default true;

    /** 自动配置列，@Field 成为非必选项。 */
    public boolean autoFiled() default true;
}
