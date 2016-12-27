/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.rdb.sharding.parser.visitor.basic.oracle;

import java.util.List;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleInsertStatement;
import com.dangdang.ddframe.rdb.sharding.parser.result.router.Condition.BinaryOperator;
import com.dangdang.ddframe.rdb.sharding.util.SQLUtil;
import com.google.common.base.Optional;

/**
 * Oracle的INSERT语句访问器.
 * 
 * @author gaohongtao
 * @author zhangliang
 */
public class OracleInsertVisitor extends AbstractOracleVisitor {

	@Override
	public boolean visit(final OracleInsertStatement x) {
		final String tableName = SQLUtil.getExactlyValue(x.getTableName().toString());
		getParseContext().setCurrentTable(tableName, Optional.fromNullable(x.getAlias()));
		List<SQLExpr> columns = x.getColumns();
		List<SQLExpr> values = x.getValues().getValues();
		for (int i = 0; i < x.getColumns().size(); i++) {
			String columnName = SQLUtil.getExactlyValue(columns.get(i).toString());
			getParseContext().addCondition(columnName, tableName, BinaryOperator.EQUAL, values.get(i),
					getDatabaseType(), getParameters());
		}
		return super.visit(x);
	}

	@Override
	public boolean visit(SQLIdentifierExpr x) {
		if (x.getParent() instanceof SQLExprTableSource) {
			printToken(x.getName());
			return false;
		} else {
			return super.visit(x);
		}
	}
}
