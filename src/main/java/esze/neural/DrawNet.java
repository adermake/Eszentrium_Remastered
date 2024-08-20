package esze.neural;

import esze.objects.Vector2D;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class DrawNet extends NeuralNetwork {

    public DrawNet(ArrayList<String> lines) {
        super(lines);

    }

    public Vector xy2nn(Vector2D v) {
        float x = (float) v.getX();
        float y = (float) v.getY();
        return new Vector(Math.sqrt(x * x + y * y), x / Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.pow(10, -20)),
                y / Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.pow(10, -20)));
    }
/*
	public float[][] getNNinpt(Vector[] nnlist,int numsamples,int numneurons,int[] numpoints) {
	    float[][] NNinpt = new float[numsamples][];
	    //numneurons*2
	    int[] sum = new int[numpoints.length+1];
	    sum[0] = 0;
	    for (int i = 1;i<sum.length;i++) {
	    	sum[i] = sum[i-1] + numpoints[i-1];
	    }
	    for (int i = 0;i< numsamples;i++) {
	    	 /* float l =

	    	        d=l/numneurons
	    	        acc=0
	    	        an1=0
	    	        an2=0
	    	        n1=0
	    	        n2=0
	    	        ll=0
	    	        k=0
	    	        for j in range(numneurons):
	    	            while acc<d and k<numpoints[i]:
	    	                ll=nnlist[0][psums[i]+k]
	    	                n1=nnlist[1][psums[i]+k]
	    	                n2=nnlist[2][psums[i]+k]
	    	                k+=1
	    	                wo=acc
	    	                wn=np.minimum(d-acc,ll)
	    	                acc+=ll
	    	                an1=woan1+wnn1
	    	                an2=woan2+wnn2
	    	            acc=acc-d
	    	            NNinpt[i,2j]=an1
	    	            NNinpt[i,2*j+1]=an2
	    	            an1=n1
	    	            an2=n2
	    	           
	    } 
	
	
	   // return NNinpt
	}
	 */
}
