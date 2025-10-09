package com.yogesh.main;

import java.io.Serializable;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.yogesh.entity.Employee;


public class StoreData {

	public static void main(String[] args) {
		
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  
	    Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
	    
	    SessionFactory sf = meta.getSessionFactoryBuilder().build();
	    Session session = sf.openSession();
	    Transaction txn = session.beginTransaction();
	    
	    Employee e1= new Employee();
	    //e1.id = 123;
//	    e1.setId(1);
	    e1.setFirstName("sat3");
	    e1.setLastName("nav3");
	    
	    Serializable x = session.save(e1);
	    //session.persist(e1);
//	    session.save(regEmp);
//	    session.save(contractEmp);
	    txn.commit();
	    
//	    Department dept = new Department();
//	    TypedQuery query = session.getNamedQuery("findEmployeeByName");    
//        query.setParameter("name","amit");   
//	    dept.getEmpList().get(0);
//	    
	    sf.close();
	    session.close();
	    System.out.println("Save Success");
	}
}
