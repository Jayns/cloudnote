package org.tedu.cloudnote.aop;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect//����ǰ���ָ��Ϊ�������
public class ExceptionBean {

	//�쳣֪ͨ�������壬������һ��Exception����,
	//�ò�������Ŀ�귽���׳����쳣����
	@AfterThrowing(throwing="e",
pointcut="within(org.tedu.cloudnote.service..*)")
	public void doExecute(Exception e){
		//���쳣��Ϣд���ļ�
		//System.out.println("���쳣��Ϣд���ļ�"+e);
		try{
			FileWriter fw = 
			new FileWriter("E:\\error.log",true);
			PrintWriter pw = new PrintWriter(fw);
			//��ӡ�쳣ͷ��
			Date date = new Date();
			SimpleDateFormat sdf = 
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = sdf.format(date);
			pw.println("******************************");
			pw.println("*  �쳣���ͣ�"+e);
			pw.println("*  ����ʱ�䣺"+dateStr);
			pw.println("*************����*************");
			//���쳣ջ��Ϣд��error.log
			e.printStackTrace(pw);
			pw.close();
			fw.close();
		}catch(Exception e1){
			System.out.println("��¼��־��������");
		}
	}
	
}
