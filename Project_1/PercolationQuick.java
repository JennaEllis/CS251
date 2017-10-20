public class PercolationQuick{
	public int[][] grid; //0-blocked, 1-open
    public int size;
	public QuickUnionUF union_find= null;
	
	public PercolationQuick(int n){
		grid = new int[n][n];
        size = n; 
		union_find = new QuickUnionUF(n*n+2);

		
	}
	
	
	public void open(int x, int y){
		//set value at (x,y) to 1
		this.grid[this.size-x-1][y] = 1;

		int n = this.size;
		int new_x = this.size - x -1;
		
		if(new_x == this.size -1){
			this.union_find.union(this.size*new_x + y, this.size*this.size);
		}

		if(new_x == 0){
			this.union_find.union(this.size*new_x + y, this.size * this.size + 1);
		}
		//check union all around coordinates
		if(new_x - 1 >=0 && this.grid[new_x -1][y]==1){
			
			this.union_find.union(new_x*n + y, (new_x-1)*n + y);
		}
		if(new_x+1 < n && this.grid[new_x+1][y]==1){
			
			this.union_find.union(new_x*n + y, (new_x + 1)*n + y);
		}
		if(y - 1 >=0 && grid[new_x][y-1]==1){
			
			this.union_find.union(new_x*n + y, new_x*n + y -1);
		}
		if(y+1 < n && grid[new_x][y+1]==1){
			
			this.union_find.union(new_x*n + y, new_x*n + y + 1);
		}

		
	}
	
	
	public boolean isOpen(int x, int y){
		if(this.grid[this.size - x -1][y] == 1){
			return true;
		}
		return false;
		
	}
	
	public boolean isFull(int x, int y){ 
		//check to see if grid[x][y] percolates to the surface (upwards in grid)
		int new_x = this.size - x - 1;

		//increment through y values 0 to n-1
		for(int i = 0; i < this.size; i++){
			if(this.union_find.connected(i, this.size*new_x + y)){
				return true;
			}
		}	

		

		return false;
	}
	
	public boolean percolates(){
		//check to see if entire system percolates
		//do each bottom square (x=0), return when encounters true from is full

		// for(int y = 0; y < this.size; y++){
		// 	if(this.isFull(0,y)){
		// 		return true;
		// 	}
		// }
		if(this.union_find.connected(this.size*this.size, this.size*this.size + 1)){
			return true;
		}else{
			return false;
		}

		
	}
	
	// public static void main(String args[]){
	
	// 	int n = StdIn.readInt();
	// 	Percolation perc = new Percolation(n); //nxn grid made, all initialized to 0
		
	// 	while(!StdIn.isEmpty()){
	// 		int x = StdIn.readInt(); 
	// 		int y = StdIn.readInt();
			
	// 		perc.open(x, y);	//open this part
	// 	}
		
        

	// 	//check to see if it percolates, print result
	// 	if(perc.percolates()){
	// 		StdOut.println("Yes");
	// 	}else{
	// 		StdOut.println("No");
	// 	}
		
	// }
	
}
