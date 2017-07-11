/*
 * Copyright 2008-2009 the original author or authors.
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
package net.hasor.db.ql.domain;
import net.hasor.db.ql.domain.inst.CompilerStack;
import net.hasor.db.ql.domain.inst.InstQueue;
/**
 * 查询选项
 * @author 赵永春(zyc@hasor.net)
 * @version : 2017-03-23
 */
public class OptionInst extends Inst {
    private String              optKey;
    private PrimitiveExpression expression;
    public OptionInst(String optKey, PrimitiveExpression expression) {
        super();
        this.optKey = optKey;
        this.expression = expression;
    }
    //
    //
    @Override
    public void doCompiler(InstQueue queue, CompilerStack stackTree) {
        queue.inst(LDC_S, this.optKey);
        this.expression.doCompiler(queue, stackTree);
        queue.inst(OPT);
    }
}