package com.demo.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // 创建流程引擎，获取默认的流程引擎
    	ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
    	// 得到流程存储服务
        RepositoryService repositoryService = engine.getRepositoryService();
        // 得到运行时服务
        RuntimeService runtimeService = engine.getRuntimeService();
        // 获取流程任务
        TaskService taskService = engine.getTaskService();
        // 部署流程文件
        repositoryService.createDeployment().addClasspathResource("processes/second_approve.bpmn").deploy();
        // 开启流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("second_approve");
        while (processInstance != null && !processInstance.isEnded()){
            List<Task> tasks = taskService.createTaskQuery().list();

            if (null != tasks && !tasks.isEmpty()){
                for (Task task: tasks){
                    if (task.getTaskDefinitionKey().equals("submitForm")){
                        System.out.println("开始提交申请");
                        Map<String, Object> variables = new HashMap();
                        variables.put("message","1");
                        variables.put("name","anna");
                        variables.put("submitTime","2019-09-01");
                        variables.put("submitType","y");
                        taskService.complete(task.getId(), variables);
                    }
                    if (task.getTaskDefinitionKey().equals("tl_approve")){
                        System.out.println("开始审批");
                        Map<String, Object> variables = new HashMap();
                        variables.put("tlApprove","y");
                        variables.put("tlMessage","anna");
                        taskService.complete(task.getId(),variables);
                    }
                }
            }
            processInstance = runtimeService
                    .createProcessInstanceQuery()
                    .processInstanceId(processInstance.getId())
                    .singleResult();
        }
        System.out.println("完成");


        // 查询第一个节点的任务并且输出
//        Task task = taskService.createTaskQuery().singleResult();
        // 完成第一个任务，相当于流程图中的申请
//        taskService.complete(task.getId());
        // 查询第二个节点的任务并且输出
//        task = taskService.createTaskQuery().singleResult();
        // 完成第二个任务，相当于流程图中的请求审核
//        taskService.complete(task.getId());
        
        
    }
}
