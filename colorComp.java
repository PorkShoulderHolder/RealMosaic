
import java.awt.*;
import java.util.*;

public class colorComp implements Comparable<colorComp>,java.io.Serializable{
	
	public Color color;
	public double[] rect= new double[4];
	public int x;
	public int y;
	public int quantity;
	public static Color benchmark;
	public static double[][] RGBconversionMatrix={{0.299,0.596,0.212},{0.587,-0.274,-0.523},{0.114,-0.322,0.311}};
	public static double[][] YIQconversionMatrix={{1.0,1.0,1.0},{0.956,-0.272,-1.105},{0.621,-0.647,1.702}};
	public String title; 
	private static final long serialVersionUID = 1L;
	public colorComp(Color color,double[] rect,int x,int y,String title){
		super();
		this.color = color;
		this.title = title;
		this.rect = rect;
		this.x = x;
		this.y = y;
		
	}
	public int compare1(colorComp a, colorComp b) {

		float[] array = new float[3];
		float[] array2 = new float[3];
		Color.RGBtoHSB(a.color.getRed(), a.color.getGreen(), a.color.getBlue(), array);
		Color.RGBtoHSB(b.color.getRed(), b.color.getGreen(), b.color.getBlue(), array2);

	//ascending order
		if(array[0]>array2[0]){
			return 1;
		}
		else if(array[0]<array2[0]){
			return -1;
		}
		else{
			return 0;
		}
		}
	
	
	@Override
	
	public int compareTo(colorComp arg0) {
		return this.quantity - arg0.quantity;
		
}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	public static Comparator<colorComp> hueComparator 
    = new Comparator<colorComp>() {

public int compare(colorComp a, colorComp b) {

	float[] array = new float[3];
	float[] array2 = new float[3];
	Color.RGBtoHSB(a.color.getRed(), a.color.getGreen(), a.color.getBlue(), array);
	Color.RGBtoHSB(b.color.getRed(), b.color.getGreen(), b.color.getBlue(), array2);

//ascending order
	if(array[0]>array2[0]){
		return 1;
	}
	else if(array[0]<array2[0]){
		return -1;
	}
	else{
		return 0;
	}

//descending order
//return fruitName2.compareTo(fruitName1);
}

};
public static  Comparator<colorComp> satComparator 
= new Comparator<colorComp>() {

	

public int compare(colorComp a, colorComp b) {

float[] array = new float[3];
float[] array2 = new float[3];
Color.RGBtoHSB(a.color.getRed(), a.color.getGreen(), a.color.getBlue(), array);
Color.RGBtoHSB(b.color.getRed(), b.color.getGreen(), b.color.getBlue(), array2);

//ascending order
if(array[1]>array2[1]){
	return 1;
}
else if(array[1]<array2[1]){
	return -1;
}
else{
	return 0;
}

//descending order
//return fruitName2.compareTo(fruitName1);
}

};


	public static Comparator<colorComp> distanceComparator 
	= new Comparator<colorComp>() {
		

public int compare(colorComp a, colorComp b) {
double A = imageReader.distance(colorComp.benchmark,a.color);
double B = imageReader.distance(colorComp.benchmark,b.color);
	

//ascending order
if(B>A){
	return 1;
}
else if(B<A){
	return -1;
}
else{
	return 0;
}

//descending order
//return fruitName2.compareTo(fruitName1);
}

};
public static Comparator<colorComp> valueComparator 
= new Comparator<colorComp>() {
public int compare(colorComp a, colorComp b) {

	
float[] array = new float[3];
float[] array2 = new float[3];
Color.RGBtoHSB(a.color.getRed(), a.color.getGreen(), a.color.getBlue(), array);
Color.RGBtoHSB(b.color.getRed(), b.color.getGreen(), b.color.getBlue(), array2);

//ascending order
if(array[2]<array2[2]){
	return 1;
}
else if(array[2]>array2[2]){
	return -1;
}
else{
	return 0;
}

//descending order
//return fruitName2.compareTo(fruitName1);
}

};
public static Comparator<colorComp> coordinateComparator 
= new Comparator<colorComp>() {
public int compare(colorComp a, colorComp b) {

	


//ascending order
if(a.y>b.y){
	return 1;
}
else if(a.y<b.y){
	return -1;
}
else if(a.x>b.x){
	return 1;
}
else if(a.x<b.x){
	return -1;
}
else return 0;

//descending order
//return fruitName2.compareTo(fruitName1);
}

};
public static Comparator<colorComp> YComparator 
= new Comparator<colorComp>() {
public int compare(colorComp a, colorComp b) {

	
double[] array = new double[3];
double[] array2 = new double[3];
double[] A = {a.color.getRed(), a.color.getGreen(), a.color.getBlue()};
double[] B = {b.color.getRed(), b.color.getGreen(), b.color.getBlue()};

array = Matrix.mult(A,RGBconversionMatrix);
array2 = Matrix.mult(B,RGBconversionMatrix);

//ascending order
if(array[0]>array2[0]){
	return 1;
}
else if(array[0]<array2[0]){
	return -1;
}
else if(array[1]>array2[1]){
	return 1;
}
else if(array[1]<array2[1]){
	return -1;
}
else if(array[2]>array2[2]){
	return 1;
}
else if(array[2]<array2[2]){
	return -1;
}
else{
	return 0;
}

//descending order
//return fruitName2.compareTo(fruitName1);
}

};

}
