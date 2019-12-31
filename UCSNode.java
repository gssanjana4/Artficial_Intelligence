
public class UCSNode implements Comparable<UCSNode> {
private long distance;
private long x,y;

public UCSNode(long distance, long x, long y) {
	this.distance = distance;
	this.x = x;
	this.y = y;
}

public boolean UCSNode(UCSNode b) {
	this.distance = b.distance;
	this.x = b.x;
	this.y = b.y;
	return(this.x==b.getX() && this.y==b.getY() && this.distance==b.getDistance());
}

public long getX() {
	return this.x;
}

public long getY() {
	return this.y;
}

public long getDistance() {
	return this.distance;
}


@Override
public boolean equals(Object c) {
	UCSNode b = (UCSNode) c;
	return (this.x == b.getX() && this.y == b.getY() && this.distance==b.getDistance());
}

@Override
public int compareTo(UCSNode o) {
	if(this.distance<o.distance)
		return -1;
	if(this.distance>o.distance)
		return 1;
	return 0;
}


}

