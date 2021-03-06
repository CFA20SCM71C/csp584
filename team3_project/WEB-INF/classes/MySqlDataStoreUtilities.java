import java.sql.*;
import java.util.*;
                	
public class MySqlDataStoreUtilities
{
static Connection conn = null;

public static void getConnection()
{

	try
	{
	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthhub","root","root");
	}
	catch(Exception e)
	{
	
	}
}


public static void deleteOrder(int orderId,String orderName)
{
	try
	{
		
		getConnection();
		String deleteOrderQuery ="Delete from customerorders where OrderId=? and orderName=?";
		PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
		pst.setInt(1,orderId);
		pst.setString(2,orderName);
		pst.executeUpdate();
	}
	catch(Exception e)
	{
			
	}
}

public static void insertOrder(int orderId,String userName,String orderName,double orderPrice,String userAddress,String creditCardNo,String FirstName,
		String LastName, String City, String State, String zip, String PhoneNumber, String CardName, String Month, String Year, String cvv, String purchaseDate, String shipDate)
{
	try
	{
	
		getConnection();
		String insertIntoCustomerOrderQuery = "INSERT INTO customerOrders(OrderId,UserName,OrderName,OrderPrice,userAddress,creditCardNo,FirstName,LastName,City,State,zip,PhoneNumber,CardName,Month,Year,cvv,purchaseDate,shipDate) "
		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";	
			
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		//set the parameter for each column and execute the prepared statement
		pst.setInt(1,orderId);
		pst.setString(2,userName);
		pst.setString(3,orderName);
		pst.setDouble(4,orderPrice);
		pst.setString(5,userAddress);
		pst.setString(6,creditCardNo);
		pst.setString(7,FirstName);
		pst.setString(8,LastName);
		pst.setString(9,City);
		pst.setString(10,State);
		pst.setString(11,zip);
		pst.setString(12,PhoneNumber);
		pst.setString(13,CardName);
		pst.setString(14,Month);
		pst.setString(15,Year);
		pst.setString(16,cvv);
		pst.setString(17,purchaseDate);
		pst.setString(18,shipDate);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}		
}

public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
{	

	HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
		
	try
	{					

		getConnection();
        //select the table 
		String selectOrderQuery ="select * from customerorders";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();	
		ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
		while(rs.next())
		{
			if(!orderPayments.containsKey(rs.getInt("OrderId")))
			{	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(rs.getInt("orderId"), arr);
			}
			ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));		
			//System.out.println("data is"+rs.getInt("OrderId")+orderPayments.get(rs.getInt("OrderId")));

			//add to orderpayment hashmap
			OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("userName"),rs.getString("orderName"),rs.getDouble("orderPrice"),
				rs.getString("userAddress"),rs.getString("creditCardNo"),rs.getString("FirstName"),rs.getString("LastName"),rs.getString("City"),
				rs.getString("State"),rs.getString("zip"),rs.getString("PhoneNumber"),rs.getString("CardName"),rs.getString("Month"),
				rs.getString("Year"),rs.getString("cvv"),rs.getString("purchaseDate"),rs.getString("shipDate"));
			listOrderPayment.add(order);
					
		}
				
					
	}
	catch(Exception e)
	{
		
	}
	return orderPayments;
}


public static void insertUser(String username,String password,String repassword,String firstname,String lastname,String email,String usertype)
{
	try
	{
		getConnection();
		String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,firstname,lastname,email,usertype) "
		+ "VALUES (?,?,?,?,?,?,?);";	
				
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
		pst.setString(1,username);
		pst.setString(2,password);
		pst.setString(3,repassword);
		pst.setString(4,firstname);
		pst.setString(5,lastname);
		pst.setString(6,email);
		pst.setString(7,usertype);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}	
}

public static HashMap<String,User> selectUser()
{	
	HashMap<String,User> hm=new HashMap<String,User>();
	try 
	{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectCustomerQuery="select * from  Registration";
		ResultSet rs = stmt.executeQuery(selectCustomerQuery);
		while(rs.next())
		{	User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("email"),rs.getString("usertype"));
				hm.put(rs.getString("username"), user);
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,StoreDetail> getStoreDetails()
{	
	HashMap<String,StoreDetail> hm=new HashMap<String,StoreDetail>();
	try 
	{
		getConnection();		
		String selectStoreDetail = "select * from storedetails";
		PreparedStatement pst = conn.prepareStatement(selectStoreDetail);
		ResultSet rs = pst.executeQuery();
	
		if (rs.next() == false) { System.out.println("ResultSet in empty in Java"); }
		int rowCount = 0;
		do {
			rowCount++;
			StoreDetail storedetails = new StoreDetail(rs.getInt("id"),rs.getString("storeId"),rs.getString("street"),rs.getString("city"),rs.getString("state"),rs.getString("zipCode"));
			hm.put(rs.getString("id"), storedetails);
		
		}
		while(rs.next());
		System.out.println("rowcount: " + rowCount);
	}
	catch(Exception e)
	{
	}
	return hm;			
}


public static void insertProduct(String ProductType,String Id,String productName,double productPrice,String productImage,String productManufacturer,
	String productCondition, Double productDiscount, Double manufacturerRebate, Integer count)
{
	try
	{	

		getConnection();
		String insertIntoProductDetailsQuery = "INSERT INTO Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,rebateAmount,count) "
		+ "VALUES (?,?,?,?,?,?,?,?,?,?);";

		PreparedStatement pst = conn.prepareStatement(insertIntoProductDetailsQuery);
		pst.setString(1,ProductType);
		pst.setString(2,Id);
		pst.setString(3,productName);
		pst.setDouble(4,productPrice);
		pst.setString(5,productImage);
		pst.setString(6,productManufacturer);
		pst.setString(7,productCondition);
		pst.setDouble(8,productDiscount);
		pst.setDouble(9,manufacturerRebate);
		pst.setInt(10,count);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}	
}

public static void insertDoctor(String id, String name,String address,String speciality,String image,String experience,Double fees, String type)
{
	try
	{	
		System.out.println("string name" + name);
		
		getConnection();
		String insertIntoDoctorsListQuery = "INSERT INTO doctorslist(id,name,address,speciality,image,experience,fees, type) "
		+ "VALUES (?,?,?,?,?,?,?,?);";
		
		System.out.println("inside insert doctor");
		
		PreparedStatement pst = conn.prepareStatement(insertIntoDoctorsListQuery);
		pst.setString(1,id);
		pst.setString(2,name);
		pst.setString(3,address);
		pst.setString(4,speciality);
		pst.setString(5,image);
		pst.setString(6,experience);
		pst.setDouble(7,fees);
		pst.setString(8,type);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}	
}

public static HashMap<String,Doctor> getDoctors()
{	
	HashMap<String,Doctor> hm=new HashMap<String,Doctor>();
	try 
	{
		getConnection();
		
		Statement stmt=conn.createStatement();
		String selectDoctor="select * from doctorslist";
		ResultSet rs = stmt.executeQuery(selectDoctor);
		while(rs.next())
		{	
			Doctor doctor = new Doctor(rs.getString("name"),rs.getString("address"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"));
			hm.put(rs.getString("Id"), doctor);
			doctor.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,Hospital> getHospitals()
{	
	HashMap<String,Hospital> hm=new HashMap<String,Hospital>();
	try 
	{
		getConnection();
		
		Statement stmt=conn.createStatement();
		String selectDoctor="select * from doctorslist";
		ResultSet rs = stmt.executeQuery(selectDoctor);
		while(rs.next())
		{	
			Hospital hospital = new Hospital(rs.getString("name"),rs.getString("address"),rs.getString("speciality"),rs.getString("image"),rs.getString("experience"),rs.getDouble("fees"),rs.getString("type"));
			hm.put(rs.getString("Id"), hospital);
			hospital.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,Tv> getTVs()
{	
	HashMap<String,Tv> hm=new HashMap<String,Tv>();
	try 
	{
		getConnection();
		
		String selectTV="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectTV);
		pst.setString(1,"Tv");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{
			Tv tv = new Tv(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), tv);
				tv.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}


public static HashMap<String,SoundSystem> getSoundSystems()
{	
	HashMap<String,SoundSystem> hm=new HashMap<String,SoundSystem>();
	try 
	{
		getConnection();
		
		String selectSoundSystem="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectSoundSystem);
		pst.setString(1,"Sound System");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	SoundSystem soundsystem = new SoundSystem(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), soundsystem);
				soundsystem.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}


public static HashMap<String,Phone> getPhones()
{	
	HashMap<String,Phone> hm=new HashMap<String,Phone>();
	try 
	{
		getConnection();
		
		String selectPhone="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectPhone);
		pst.setString(1,"Phone");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Phone phone = new Phone(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), phone);
				phone.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}


public static HashMap<String,Laptop> getLaptops()
{	
	HashMap<String,Laptop> hm=new HashMap<String,Laptop>();
	try 
	{
		getConnection();
		
		String selectLaptop="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectLaptop);
		pst.setString(1,"Laptop");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	Laptop laptop = new Laptop(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), laptop);
				laptop.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}


public static HashMap<String,VoiceAssistant> getVoiceAssistants()
{	
	HashMap<String,VoiceAssistant> hm=new HashMap<String,VoiceAssistant>();
	try 
	{
		getConnection();
		
		String selectVoiceAssistant="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectVoiceAssistant);
		pst.setString(1,"Voice Assistant");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	VoiceAssistant voiceassistant = new VoiceAssistant(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), voiceassistant);
				voiceassistant.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}


public static HashMap<String,FitnessWatch> getFitnessWatches()
{	
	HashMap<String,FitnessWatch> hm=new HashMap<String,FitnessWatch>();
	try 
	{
		getConnection();
		
		String selectFitnessWatch="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectFitnessWatch);
		pst.setString(1,"Fitness Watch");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	FitnessWatch fitnesswatch = new FitnessWatch(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), fitnesswatch);
				fitnesswatch.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}


public static HashMap<String,SmartWatch> getSmartWatches()
{	
	HashMap<String,SmartWatch> hm=new HashMap<String,SmartWatch>();
	try 
	{
		getConnection();
		
		String selectSmartWatch="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectSmartWatch);
		pst.setString(1,"Smart Watch");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	SmartWatch smartwatch = new SmartWatch(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), smartwatch);
				smartwatch.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}


public static HashMap<String,HeadPhone> getHeadPhones()
{	
	HashMap<String,HeadPhone> hm=new HashMap<String,HeadPhone>();
	try 
	{
		getConnection();
		
		String selectHeadPhone="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectHeadPhone);
		pst.setString(1,"HeadPhone");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	HeadPhone headphone = new HeadPhone(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), headphone);
				headphone.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}


public static HashMap<String,VirtualReality> getVirtualRealitys()
{	
	HashMap<String,VirtualReality> hm=new HashMap<String,VirtualReality>();
	try 
	{
		getConnection();
		
		String selectVirtualReality="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectVirtualReality);
		pst.setString(1,"Virtual Reality");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	VirtualReality virtualreality = new VirtualReality(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), virtualreality);
				virtualreality.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,PetTracker> getPetTrackers()
{
	HashMap<String,PetTracker> hm=new HashMap<String,PetTracker>();
	try
	{
		getConnection();

		String selectPetTracker="select * from  Productdetails where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectPetTracker);
		pst.setString(1,"Pet Tracker");
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{	PetTracker pettracker = new PetTracker(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
			hm.put(rs.getString("Id"), pettracker);
			pettracker.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}


public static String addproducts(String ProductType,String Id,String productName,double productPrice,String productImage,String productManufacturer,
	String productCondition,double productDiscount,double rebateAmount,Integer count,String prod)
{
	String msg = "Product is added successfully";
	try{
		
		getConnection();
		String addProductQurey = "INSERT INTO  Productdetails(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,rebateAmount,count)" +
		"VALUES (?,?,?,?,?,?,?,?,?,?);";

			PreparedStatement pst = conn.prepareStatement(addProductQurey);
			pst.setString(1,ProductType);
			pst.setString(2,Id);
			pst.setString(3,productName);
			pst.setDouble(4,productPrice);
			pst.setString(5,productImage);
			pst.setString(6,productManufacturer);
			pst.setString(7,productCondition);
			pst.setDouble(8,productDiscount);
			pst.setDouble(9,rebateAmount);
			pst.setInt(10,count);
			pst.executeUpdate();

			try{
				if (!prod.isEmpty())
				{
					String addaprodacc =  "INSERT INTO  Product_accessories(productName,accessoriesName)" +
					"VALUES (?,?);";
					PreparedStatement pst1 = conn.prepareStatement(addaprodacc);
					pst1.setString(1,prod);
					pst1.setString(2,Id);
					pst1.executeUpdate();
				}
			}catch(Exception e)
			{
				msg = "Error while adding the product";
				e.printStackTrace();
		
			}	
	}
	catch(Exception e)
	{
		msg = "Error while adding the product";
		e.printStackTrace();	
	}
	return msg;
}


public static String updateproducts(String ProductType,String Id,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,double rebateAmount,Integer count)
{
    String msg = "Product is updated successfully";
	try{
		
		getConnection();
		String updateProductQurey = "UPDATE Productdetails SET productName=?,productPrice=?,productImage=?,productManufacturer=?,productCondition=?,productDiscount=?,rebateAmount=?,count=? where Id =?;" ;
		
		   
				        			
			PreparedStatement pst = conn.prepareStatement(updateProductQurey);
			
			pst.setString(1,productName);
			pst.setDouble(2,productPrice);
			pst.setString(3,productImage);
			pst.setString(4,productManufacturer);
			pst.setString(5,productCondition);
			pst.setDouble(6,productDiscount);
			pst.setDouble(7,rebateAmount);
			pst.setInt(8,count);
			pst.setString(9,Id);
			pst.executeUpdate();
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();	
	}
 return msg;	
}


public static String deleteproducts(String Id)
{   String msg = "Product is deleted successfully";
	try
	{
		
		getConnection();
		String deleteproductsQuery ="Delete from Productdetails where Id=?";
		PreparedStatement pst = conn.prepareStatement(deleteproductsQuery);
		pst.setString(1,Id);
		
		pst.executeUpdate();
	}
	catch(Exception e)
	{
			msg = "Proudct cannot be deleted";
	}
	return msg;
}



public static HashMap<String, Product> getInventoryProducts()
{
	HashMap<String, Product> products=new HashMap<String, Product>();
	try
	{
		getConnection();
		String selectProductQuery ="select * from Productdetails;";
		PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
		ResultSet rs = pst1.executeQuery();
		while(rs.next())
		{
			Product product = new Product();
			product.setId(rs.getString("Id"));
			product.setProductType(rs.getString("ProductType"));
			product.setproductName(rs.getString("productName"));
			product.setproductPrice(rs.getDouble("productPrice"));
			product.setproductImage(rs.getString("productImage"));
			product.setproductManufacturer(rs.getString("productManufacturer"));
			product.setproductCondition(rs.getString("productDiscount"));
			product.setproductDiscount(rs.getDouble("productDiscount"));
			product.setrebateAmount(rs.getDouble("rebateAmount"));
			product.setcount(rs.getInt("count"));

			products.put(product.getId(),product);
		}

	}
	catch(Exception e)
	{

	}
	return products;
}


public static String UpdateInventory(String orderName)
{
    String msg = "Product is updated successfully";
	try{

		getConnection();
		String updateProductQurey = "UPDATE Productdetails SET count=count-1 where productName =?;" ;



			PreparedStatement pst = conn.prepareStatement(updateProductQurey);

			pst.setString(1,orderName);
			pst.executeUpdate();
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();
	}
 return msg;
}


public static HashMap<String, Product> getProductsOnSale()
{
	HashMap<String, Product> products=new HashMap<String, Product>();
	try
	{
		getConnection();
		String selectProductQuery ="select * from Productdetails where count>0;";
		PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
		ResultSet rs = pst1.executeQuery();
		while(rs.next())
		{
			Product product = new Product();
			product.setId(rs.getString("Id"));
			product.setProductType(rs.getString("ProductType"));
			product.setproductName(rs.getString("productName"));
			product.setproductPrice(rs.getDouble("productPrice"));
			product.setproductImage(rs.getString("productImage"));
			product.setproductManufacturer(rs.getString("productManufacturer"));
			product.setproductCondition(rs.getString("productDiscount"));
			product.setproductDiscount(rs.getDouble("productDiscount"));
			product.setrebateAmount(rs.getDouble("rebateAmount"));
			product.setcount(rs.getInt("count"));

			products.put(product.getId(),product);
		}

	}
	catch(Exception e)
	{

	}
	return products;
}


public static HashMap<String, Product> getProductsRebate()
{
	HashMap<String, Product> products=new HashMap<String, Product>();
	try
	{
		getConnection();
		String selectProductQuery ="select * from Productdetails where rebateAmount>0;";
		PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
		ResultSet rs = pst1.executeQuery();
		while(rs.next())
		{
			Product product = new Product();
			product.setId(rs.getString("Id"));
			product.setProductType(rs.getString("ProductType"));
			product.setproductName(rs.getString("productName"));
			product.setproductPrice(rs.getDouble("productPrice"));
			product.setproductImage(rs.getString("productImage"));
			product.setproductManufacturer(rs.getString("productManufacturer"));
			product.setproductCondition(rs.getString("productDiscount"));
			product.setproductDiscount(rs.getDouble("productDiscount"));
			product.setrebateAmount(rs.getDouble("rebateAmount"));
			product.setcount(rs.getInt("count"));

			products.put(product.getId(),product);
		}

	}
	catch(Exception e)
	{

	}
	return products;
}


public static HashMap<String, Product> getSalesReport()
{
	HashMap<String, Product> products=new HashMap<String, Product>();
	try
	{
		getConnection();
		String selectProductQuery ="select orderName, orderPrice, count(orderName) as cnt, (orderPrice*count(orderName)) as total from customerorders group by orderName;";
		PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
		ResultSet rs = pst1.executeQuery();
		while(rs.next())
		{
			Product product = new Product();
			product.setproductName(rs.getString("orderName"));
			product.setproductPrice(rs.getDouble("orderPrice"));
			product.setnum_items(rs.getInt("cnt"));
			product.settotal_sales(rs.getDouble("total"));

			products.put(product.getproductName(),product);
		}

	}
	catch(Exception e)
	{

	}
	return products;
}


public static HashMap<String, Product> getDailySales()
{
	HashMap<String, Product> products=new HashMap<String, Product>();
	try
	{
		getConnection();
		String selectProductQuery ="select purchaseDate, sum(orderPrice) as total from customerorders group by purchaseDate;";
		PreparedStatement pst1 = conn.prepareStatement(selectProductQuery);
		ResultSet rs = pst1.executeQuery();
		while(rs.next())
		{
			Product product = new Product();
			product.setdates(rs.getString("purchaseDate"));
			product.settotal_sales(rs.getDouble("total"));

			products.put(product.getdates(),product);
		}

	}
	catch(Exception e)
	{

	}
	return products;
}


}
