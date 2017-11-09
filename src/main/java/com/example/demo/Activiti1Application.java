package com.example.demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Activiti1Application {

	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcessEngine processEngine;

	public static void main(String[] args) {
		SpringApplication.run(Activiti1Application.class, args);
	}

	@RequestMapping("/")
	String index() {
		System.out.println("init taskService" + taskService);
		System.out.println("init processEngine" + processEngine);
		return "init";
	}
	
	/**
	 * 部署流程定义
	 */
	@GetMapping("/deploy/classpath")
	public void deployWithClassPath() {
		Deployment deployment = processEngine.getRepositoryService() //获取部署相关实例
				.createDeployment().name("HelloWorld") //创建部署
				.addClasspathResource("/diagrams/HelloWorld.bpmn")//加载资源文件
				.addClasspathResource("/diagrams/HelloWorld.png").deploy();//部署
		System.out.println("HelloWorld流程部署,ID:"+deployment.getId()+",name:"+deployment.getName()+",时间:"
				+deployment.getDeploymentTime());
	}
	
	/**
	 * 启动流程实例
	 */
	@GetMapping("/start")
	public void start() {
		ProcessInstance processInstance = processEngine.getRuntimeService()//获取运行时相关实例
				.startProcessInstanceById("helloWorldProcess");
		System.out.println("HelloWorld启动流程,ID:"+processInstance.getId()+",Name:"+processInstance.getName()
				+",DeploymentId:"+processInstance.getDeploymentId()+",ProcessInstanceId:"+processInstance.getProcessInstanceId()+",ProcessDefinitionId:"+processInstance.getProcessDefinitionId());
	}

	@RequestMapping("/task")
	String task() {
		System.out.println("task" + taskService);
		return taskService.toString();
	}
}
