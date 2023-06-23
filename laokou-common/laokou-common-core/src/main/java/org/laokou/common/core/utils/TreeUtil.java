/**
 * Copyright (c) 2022 KCloud-Platform-Alibaba Authors. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *   http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.laokou.common.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.laokou.common.i18n.core.CustomException;

import java.util.*;

/**
 * @author laokou
 */
@Data
public class TreeUtil {

	public static <T> TreeNode<T> rootRootNode(String name) {
		return new TreeNode<>(0L, name, null, new ArrayList<>(0));
	}

	public static <T> TreeNode<T> rootRootNode() {
		return rootRootNode("根节点");
	}

	public static <T extends TreeNode<T>> T buildTreeNode(List<T> treeNodes, T rootNode) {
		if (null == rootNode) {
			throw new CustomException("请构造根节点");
		}
		treeNodes.add(rootNode);
		// list转map
		Map<Long, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
		for (T treeNode : treeNodes) {
			nodeMap.put(treeNode.getId(), treeNode);
		}
		for (T treeNo : treeNodes) {
			T parent = nodeMap.get(treeNo.getPid());
			if (parent != null && treeNo.getPid().equals(parent.getId())) {
				if (CollectionUtil.isEmpty(parent.getChildren())) {
					parent.setChildren(new ArrayList<>(0));
				}
				parent.getChildren().add(treeNo);
			}
		}
		return rootNode;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TreeNode<T> {

		private Long id;

		private String name;

		private Long pid;

		private List<T> children;

	}

}
