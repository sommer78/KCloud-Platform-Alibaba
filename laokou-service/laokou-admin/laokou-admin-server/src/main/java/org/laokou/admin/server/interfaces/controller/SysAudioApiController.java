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

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.laokou.admin.client.dto.SysResourceAuditDTO;
import org.laokou.admin.client.vo.SysResourceVO;
import org.laokou.admin.server.application.service.SysResourceApplicationService;
import org.laokou.admin.server.application.service.WorkflowTaskApplicationService;
import org.laokou.admin.server.interfaces.qo.SysResourceQo;
import org.laokou.common.i18n.core.HttpResult;
import org.laokou.common.lock.annotation.Lock4j;
import org.laokou.common.log.annotation.OperateLog;
import org.laokou.common.log.vo.SysAuditLogVO;
import org.laokou.common.oss.vo.UploadVO;
import org.laokou.common.redis.utils.RedisKeyUtil;
import org.laokou.common.trace.annotation.TraceLog;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author laokou
 */
@RestController
@Tag(name = "Sys Resource Audio API", description = "音频管理API")
@RequestMapping("/sys/resource/audio/api")
@RequiredArgsConstructor
public class SysAudioApiController {

	private final SysResourceApplicationService sysResourceApplicationService;

	private final WorkflowTaskApplicationService workflowTaskApplicationService;

	@GetMapping("/auditLog")
	@TraceLog
	@Parameter(name = "businessId", description = "业务id", required = true, example = "123")
	@Operation(summary = "音频管理>审批日志", description = "音频管理>审批日志")
	@PreAuthorize("hasAuthority('sys:resource:audio:auditLog')")
	public HttpResult<List<SysAuditLogVO>> auditLog(@RequestParam("businessId") Long businessId) {
		return new HttpResult<List<SysAuditLogVO>>().ok(sysResourceApplicationService.queryAuditLogList(businessId));
	}

	@PostMapping("/syncIndex")
	@TraceLog
	@Operation(summary = "音频管理>同步索引", description = "音频管理>同步索引")
	@OperateLog(module = "音频管理", name = "同步索引")
	@Lock4j(key = "audio_sync_index_lock")
	@PreAuthorize("hasAuthority('sys:resource:audio:syncIndex')")
	public HttpResult<Boolean> syncIndex() throws InterruptedException {
		return new HttpResult<Boolean>()
				.ok(sysResourceApplicationService.syncResource("audio", RedisKeyUtil.getSyncIndexKey("audio")));
	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@TraceLog
	@Operation(summary = "音频管理>上传", description = "音频管理>上传")
	public HttpResult<UploadVO> upload(@RequestPart("file") MultipartFile file, @RequestParam("md5") String md5)
			throws Exception {
		return new HttpResult<UploadVO>().ok(sysResourceApplicationService.uploadResource("audio", file, md5));
	}

	@PostMapping("/query")
	@Operation(summary = "音频管理>查询", description = "音频管理>查询")
	@TraceLog
	@PreAuthorize("hasAuthority('sys:resource:audio:query')")
	public HttpResult<IPage<SysResourceVO>> query(@RequestBody SysResourceQo qo) {
		return new HttpResult<IPage<SysResourceVO>>().ok(sysResourceApplicationService.queryResourcePage(qo));
	}

	@GetMapping(value = "/detail")
	@Operation(summary = "音频管理>详情", description = "音频管理>详情")
	@TraceLog
	@PreAuthorize("hasAuthority('sys:resource:audio:detail')")
	public HttpResult<SysResourceVO> detail(@RequestParam("id") Long id) {
		return new HttpResult<SysResourceVO>().ok(sysResourceApplicationService.getResourceById(id));
	}

	@GetMapping(value = "/download")
	@TraceLog
	@Operation(summary = "音频管理>下载", description = "音频管理>下载")
	@PreAuthorize("hasAuthority('sys:resource:audio:download')")
	public void download(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
		sysResourceApplicationService.downLoadResource(id, response);
	}

	@PostMapping(value = "/insert")
	@TraceLog
	@Operation(summary = "音频管理>新增", description = "音频管理>新增")
	@OperateLog(module = "音频管理", name = "音频新增")
	@PreAuthorize("hasAuthority('sys:resource:audio:insert')")
	public HttpResult<Boolean> insert(@RequestBody SysResourceAuditDTO dto) throws IOException {
		return new HttpResult<Boolean>().ok(sysResourceApplicationService.insertResource(dto));
	}

	@PutMapping(value = "/update")
	@TraceLog
	@Operation(summary = "音频管理>修改", description = "音频管理>修改")
	@OperateLog(module = "音频管理", name = "音频修改")
	@PreAuthorize("hasAuthority('sys:resource:audio:update')")
	public HttpResult<Boolean> update(@RequestBody SysResourceAuditDTO dto) throws IOException {
		return new HttpResult<Boolean>().ok(sysResourceApplicationService.updateResource(dto));
	}

	@DeleteMapping(value = "/delete")
	@TraceLog
	@Operation(summary = "音频管理>删除", description = "音频管理>删除")
	@OperateLog(module = "音频管理", name = "音频删除")
	@PreAuthorize("hasAuthority('sys:resource:audio:delete')")
	public HttpResult<Boolean> delete(@RequestParam("id") Long id) {
		return new HttpResult<Boolean>().ok(sysResourceApplicationService.deleteResource(id));
	}

	@GetMapping(value = "/diagram")
	@TraceLog
	@Operation(summary = "音频管理>流程图", description = "音频管理>流程图")
	@PreAuthorize("hasAuthority('sys:resource:audio:diagram')")
	public HttpResult<String> diagram(@RequestParam("processInstanceId") String processInstanceId) throws IOException {
		return new HttpResult<String>().ok(workflowTaskApplicationService.diagramProcess(processInstanceId));
	}

}
