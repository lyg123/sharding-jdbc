package com.dangdang.ddframe.rdb.sharding.parser.visitor.or;

import com.alibaba.druid.sql.ast.SQLObject;
import com.dangdang.ddframe.rdb.sharding.parser.visitor.SQLVisitor;
import com.dangdang.ddframe.rdb.sharding.parser.visitor.or.node.AbstractOrASTNode;
import com.google.common.base.Optional;

public interface OrVisitor extends SQLVisitor {

	/**
	 * 进行OR表达式的访问.
	 *
	 * @param sqlObject SQL对象
	 * @return OR访问节点
	 */
	public abstract Optional<AbstractOrASTNode> visitHandle(SQLObject sqlObject);

}