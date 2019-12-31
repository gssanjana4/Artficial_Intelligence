
public class BFSNode implements Comparable<BFSNode>{
private long x;
private long y;

public BFSNode(long x, long y) {
	this.x = x;
	this.y = y;
}

public boolean BFSNode(BFSNode b) {
	this.x = b.x;
	this.y = b.y;
	return(this.x==b.getX() && this.y==b.getY());
}

public long getX() {
	return this.x;
}

public long getY() {
	return this.y;
}


@Override
public boolean equals(Object c) {
	BFSNode b = (BFSNode) c;
	return (this.x == b.getX() && this.y == b.getY());
}

@Override
public int hashCode() {
	return (int) ((17*31*x)^y);
}

@Override
public int compareTo(BFSNode arg0) {
	// TODO Auto-generated method stub
	return 0;
}
}
