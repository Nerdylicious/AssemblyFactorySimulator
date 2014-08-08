package scheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.concurrent.locks.Condition;

/*
 * This class stores a list of products.  
 * 
 * Its main functionality is to load a list from a file.  
 * 
 * It can also place an order given a product name since it store the list.   
 * 
 */

public class ProductList {
	private LinkedList <Product>products;
	
	public ProductList(MachineList ml, AssemblyList al) {
		products = new LinkedList<Product>();	
		loadProductList(ml,al);
	}	
	
	void loadProductList(MachineList ml, AssemblyList al) {
		String filename = "products.txt";
		try {
			FileReader in = new FileReader(filename);    		
			BufferedReader br = new BufferedReader(in);			
			String linein;		
			while((linein = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(linein);
				String name = st.nextToken();
				int numofsubassemblies = Integer.parseInt(st.nextToken());
				//System.out.println(name + " " + numofsubassemblies);
				Product product = new Product(name,numofsubassemblies,br,ml,al);
				products.add(product);				
			}
		} catch (Exception  e) {
			System.out.println(e);			 
		}				
	}
	
	public Product getProduct(String name) {
		for (Product product: products) {
			if (product.getName().contentEquals(name)) {
				return product;
			}			
		}
		return null;	
	}
	
	public String toString() {
		String treturn = "";
		for (Product product: products) {
			treturn += product.toString() + "\n";
		}					
		return treturn ;	
	}
	
	public Order placeOrder(String productname, String ordername) {
		for (Product product: products) {
			if (product.MatchName(productname)) {
				return product.placeOrder(ordername);
			}
		}	
		return null; // should never be here
	}

}
