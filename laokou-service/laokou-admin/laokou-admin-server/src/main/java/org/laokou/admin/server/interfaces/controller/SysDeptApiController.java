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
package org.laokou.admin.server.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.laokou.admin.server.application.service.SysDeptApplicationService;
import org.laokou.admin.client.dto.SysDeptDTO;
import org.laokou.admin.server.interfaces.qo.SysDeptQo;
import org.laokou.common.data.cache.annotation.DataCache;
import org.laokou.common.data.cache.enums.CacheEnum;
import org.laokou.common.i18n.core.HttpResult;
import org.laokou.admin.client.vo.SysDeptVO;
import org.laokou.common.log.annotation.OperateLog;
import org.laokou.common.trace.annotation.TraceLog;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * 系统部门
 *
 * @author laokou
 */
@RestController
@Tag(name = "Sys Dept API", description = "系统部门API")
@RequestMapping("/sys/dept/api")
@RequiredArgsConstructor
public class SysDeptApiController {

	private final SysDeptApplicationService sysDeptApplicationService;

	@Operation(summary = "系统部门>树菜单", description = "系统部门>树菜单")
	@GetMapping("/tree")
	@TraceLog
	public HttpResult<SysDeptVO> tree() {
		return new HttpResult<SysDeptVO>().ok(sysDeptApplicationService.treeDept());
	}

	@PostMapping("/query")
	@Operation(summary = "系统部门>查询", description = "系统部门>查询")
	@PreAuthorize("hasAuthority('sys:dept:query')")
	@TraceLog
	public HttpResult<List<SysDeptVO>> query(@RequestBody SysDeptQo qo) {
		return new HttpResult<List<SysDeptVO>>().ok(sysDeptApplicationService.queryDeptList(qo));
	}

	@PostMapping("/insert")
	@Operation(summary = "系统部门>新增", description = "系统部门>新增")
	@OperateLog(module = "系统部门", name = "部门新增")
	@PreAuthorize("hasAuthority('sys:dept:insert')")
	@TraceLog
	public HttpResult<Boolean> insert(@RequestBody SysDeptDTO dto) {
		return new HttpResult<Boolean>().ok(sysDeptApplicationService.insertDept(dto));
	}

	@PutMapping("/update")
	@Operation(summary = "系统部门>修改", description = "系统部门>修改")
	@OperateLog(module = "系统部门", name = "部门修改")
	@PreAuthorize("hasAuthority('sys:dept:update')")
	@TraceLog
	@DataCache(name = "dept", key = "#dto.id", type = CacheEnum.DEL)
	public HttpResult<Boolean> update(@RequestBody SysDeptDTO dto) {
		return new HttpResult<Boolean>().ok(sysDeptApplicationService.updateDept(dto));
	}

	@GetMapping("/detail")
	@TraceLog
	@Operation(summary = "系统部门>详情", description = "系统部门>详情")
	@DataCache(name = "dept", key = "#id")
	public HttpResult<SysDeptVO> detail(@RequestParam("id") Long id) {
		return new HttpResult<SysDeptVO>().ok(sysDeptApplicationService.getDept(id));
	}

	@DeleteMapping("/delete")
	@TraceLog
	@Operation(summary = "系统部门>删除", description = "系统部门>删除")
	@OperateLog(module = "系统部门", name = "部门删除")
	@PreAuthorize("hasAuthority('sys:dept:delete')")
	@DataCache(name = "dept", key = "#id", type = CacheEnum.DEL)
	public HttpResult<Boolean> delete(@RequestParam("id") Long id) {
		return new HttpResult<Boolean>().ok(sysDeptApplicationService.deleteDept(id));
	}

	@GetMapping("/get")
	@TraceLog
	@Operation(summary = "系统部门>部门树ids", description = "系统部门>部门树ids")
	public HttpResult<List<Long>> get(@RequestParam(value = "roleId") Long roleId) {
		return new HttpResult<List<Long>>().ok(sysDeptApplicationService.getDeptIdsByRoleId(roleId));
	}

}
