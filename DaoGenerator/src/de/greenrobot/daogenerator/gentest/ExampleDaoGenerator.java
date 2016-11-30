package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Index;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class ExampleDaoGenerator {
	public static void main(String[] args) throws Exception {
		// 在src-test中执行出来

////		Schema schema = new Schema(1, "de.greenrobot.daoexample");
//		// 版本号2.0 2014-07-23
//		Schema schema = new Schema(2, "com.xxx.database.dao");
//		// 配置表
//		addConfigInfo(schema);
//
//		// 消息表
//		addMsgInfo(schema);
//
//		// 科目表
//		addCourse(schema);
//
//		// 课程表
//		addCourseList(schema);
//
//		new DaoGenerator().generateAll(schema, "./app/src/main/java");

//		new DaoGenerator().generateAll(schema, "./appxxx/src/main/java");

	}
	
	/**
	 *  配置表
	 * @param
	 */
	private static void addConfigInfo(Schema schema){
		Entity configInfo = schema.addEntity("ConfigInfo");
		// 配置参数
		configInfo.addStringProperty("name").notNull().primaryKey();
		// 配置值
		configInfo.addStringProperty("value");

	}

	/**
	 *
	 * @param schema
	 */
	private static void addMsgInfo(Schema schema){
		Entity msg = schema.addEntity("MsgInfo");
		// 消息ID
		msg.addIdProperty().primaryKey();
		// 标题
		msg.addStringProperty("title");
		// 消息内容
		msg.addStringProperty("msgcontext");
		// 用户标识
		msg.addStringProperty("username");
		// 消息类型 大类 1、系统消息 2答疑消息
		msg.addIntProperty("msgtypeA");
		// 消息类型 小类 1A通知类(新界面处理)1B视频类 1C做题类 ;  2A
		msg.addIntProperty("msgtypeB");
		// 添加时间
		msg.addDateProperty("addtime");
		// 部分有图片
		msg.addDateProperty("image");
		// 是否己读
		msg.addBooleanProperty("bread");
		// 是否删除
		msg.addBooleanProperty("bdel");
		// 备注 （扩展用)
		msg.addBooleanProperty("remark");
	}

	/**
	 * 科目表
	 * @param schema
	 */
	private static void addCourse(Schema schema){
		Entity course = schema.addEntity("CourseInfo");
		// 主键  科目ID
		Property property1 = course.addIntProperty("kemuID").getProperty();
		// 科目名字
		course.addStringProperty("kemuName");
		// 年级ID
		Property property2 = course.addIntProperty("clsID").getProperty();//.index();  // 设置索引
		// 用户名
		Property property3 = course.addStringProperty("username").getProperty();

		Index index = new Index();
		index.addProperty(property1); // 科目ID
		index.addProperty(property2); // 年级ID
		index.addProperty(property3); // 用户名

		course.addIndex(index);// 多列索引
	}

	/**
	 * 年级科目表
	 * @param schema
	 */
	private static void addCourseList(Schema schema){
		Entity courseList = schema.addEntity("CourseListInfo");
		// 年级
		courseList.addIntProperty("clsID").primaryKey();
		courseList.addStringProperty("clsName");
		// 科目
		courseList.addIntProperty("kemuID");
		courseList.addStringProperty("kemuName");
		// 课程
		courseList.addIntProperty("courseID");
		courseList.addStringProperty("courseName");
		// 老师名
		courseList.addStringProperty("teacherName");
		// 播放CCID
		courseList.addStringProperty("ccid");
		// URL
		courseList.addIntProperty("cousrURL");

	}
////////////////////////////////////////////////////////////////////////////

//		// 主键  科目ID
//		Property property1 = course.addIntProperty("kemuID").getProperty();
//		// 科目名字
//		course.addStringProperty("kemuName");
//		// 年级ID
//		Property property2 = course.addIntProperty("clsID").getProperty();//.index();  // 设置索引
//
//		Index index = new Index();
//		index.addProperty(property1);
//		index.addProperty(property2);
//		course.addIndex(index);// 多列索引
//	/**
//	 * 年级科目表
//	 * @param schema
//	 */
//	private static void addCourseList(Schema schema){
//		Entity courseList = schema.addEntity("CourseListInfo");
//		// 年级
//		courseList.addIntProperty("clsID").primaryKey();
//		courseList.addStringProperty("clsName");
//		// 科目
//		courseList.addIntProperty("kemuID");
//		courseList.addStringProperty("kemuName");
//		// 课程
//		courseList.addIntProperty("courseID");
//		courseList.addStringProperty("courseName");
//		// 老师名
//		courseList.addStringProperty("teacherName");
//		// 播放CCID
//		courseList.addStringProperty("ccid");
//		// URL
//		courseList.addIntProperty("cousrURL");
//
//	}
	
	private static void addMsg(Schema schema){
		Entity msg = schema.addEntity("MsgInfo");
		msg.addIdProperty().primaryKey().autoincrement();
		msg.addStringProperty("msgtext");
		msg.addDateProperty("addtime");
	}
//	1.创建一个实体类
//	Entity note = schema.addEntity("Note");
//	dao.setTableName("NoteList"); 默认表名就是类名，也可以自定义表名
//	// 设置一个自增长ID列为主键：
//	dao.addIdProperty().primaryKey().autoincrement();
//	dao.addIntProperty("cityId");
//	dao.addStringProperty("infoType").notNull();//非null字段
//	dao.addDoubleProperty("Id");
	
	private static void addNote(Schema schema) {
		// 创建一个实体类
		Entity note = schema.addEntity("Note"); // 表名就是类名，也可以自定义表名
		
		note.addIdProperty();
		note.addStringProperty("text").notNull();
		note.addStringProperty("comment");
		note.addDateProperty("date");
	}

	private static void addCustomerOrder(Schema schema) {
		Entity customer = schema.addEntity("Customer");
		customer.addIdProperty();
		customer.addStringProperty("name").notNull();

		Entity order = schema.addEntity("Order");
		order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
		order.addIdProperty();
		Property orderDate = order.addDateProperty("date").getProperty();
		Property customerId = order.addLongProperty("customerId").notNull()
				.getProperty();
		order.addToOne(customer, customerId);

		ToMany customerToOrders = customer.addToMany(order, customerId);
		customerToOrders.setName("orders");
		customerToOrders.orderAsc(orderDate);
	}
}
