package path_finding;

import java.util.Comparator;

/**
 * CompareVertices is a Comparator that allows for 
 * Vertex objects to be ordered with respect to their
 * ETC value. Note: this comparison is made on floats. 
 * So the margin of error is 0.001.
 * @author James Woods
 *
 */
public class CompareEtc extends Search_Comparison implements Comparator<Pair> {
	
	public CompareEtc() {
		super();
		this.count = 0;
	}
	
	
	@Override
	public int compare(Pair o1, Pair o2) {
		float comparison  = o1.getVert().getEtc() - o2.getVert().getEtc();
		int toReturn = 0;
		
		if(comparison == 0)
			toReturn = 0;
		else if(comparison < 0.001)
			toReturn = -1;
		else if(comparison > 0.001)
			toReturn = 1;
		
		count++;
		
		return toReturn;
	}
}
