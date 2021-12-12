#!/usr/bin/env groovy
def LastFrameScore(var, spare,strike){
     def total = 0
      def score = var[0]+var[1];
      /*println score;
      println spare;
      println strike;
      */
      if (spare[0] ) {
        total += score;
      }

      if (strike[0] ) {
        total += score
      }
      if(strike[1] ) {
        total += score
      }
      total += score;
    
     return total;
}
def Score(var, spare,strike){
      def total = 0
      def score = var[0]+var[1];
      /*println score;
      println spare;
      println strike;
      */
      if (spare[0] ) {
   	total += score;
	}
  	  
	if (strike[0] ) {
   	total += score
	}
        if(strike[1] ) {
  	total += score 
	}
      total += score;
     //println total;
     return total;
}
def Strike(List var) {
    def total=var[0]+var[1]; 
    if( ( var[0] == 10 ) && ( total == 10 ) ) {
      return true;
    } else { 
       return false;
    }
}   
def Spare(List var) {
    def total=var[0]+var[1];
    if ( (total == 10) && ( var[0] != 10) ) {
       return true;
     } else {
       return false ; 
     }
}
//def st = [[10,0], [10,0], [10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0] ];
//def st = [[5,5], [5,5], [5,5], [5,5], [5,5],[5,5],[5,5],[5,5],[5,5],[5,5],[5,5],[5,5] ];
//def st = [[5,0], [5,0], [5,0],[5,0],[5,0],[5,0],[5,0],[5,0],[5,0],[5,0],[5,0],[5,0] ];

def bollingScore(List st) {
  int Total=0;
  int Frame=10;
  def spare = [false,false]; //pre pre-pre
  def strike = [false,false];//pre pre-pre
 
//get 10 Frame and Loop:
def tenst = st.subList(0,Frame);
def bonus = st.subList(Frame,Frame+2)
//println tenst;
//println bonus;
//System.exit(1);
tenst.eachWithIndex{ var, index ->
       /*println "index:"+index
       println var;
       */
       Total +=  Score(var,spare,strike);
       /*println Score(var,spare,strike);
       println "total:"+Total 
       */
      //update status  
      strike[1]=strike[0];
      spare[1]=spare[0];
      strike[0]=Strike(var)
      spare[1]=spare[0];
      spare[0]=Spare(var);     
}


//if lastFrame is spare
println "spare:"+spare[0]
if (spare[0] ) {
      //println tmp;
      def var=bonus[0]
      //println var;
      Total += var[0]+var[1];
      println "Total:"+Total;
}

//if lastFrame is strike
println "strike:"+strike[0]
def status=false;
def val;


if(strike[0]) {
    //println "inside if"
    //println bonus;
    bonus.eachWithIndex{ var, index ->      
       //println var;
       println "index:"+index
    
      val=var[0]+var[1];
      println status
      if (status) {
          Total += 2*val;
       }else {
          Total +=val;        
       }
       println "Total:"+Total
      
       if(val == 10 ){ 
         status=true;
       }
   
   }
}

}//bollingScore

/////Testing cases////
def stArray =[ [[10,0], [10,0], [10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0] ], \
       [[5,5], [5,5], [5,5], [5,5], [5,5],[5,5],[5,5],[5,5],[5,5],[5,5],[5,5],[5,5] ],  \
       [[5,0], [5,0], [5,0],[5,0],[5,0],[5,0],[5,0],[5,0],[5,0],[5,0],[5,0],[5,0] ] ]	

def expectVal=[300, 200, 50]			

for (i=0; i<3; i++) {
    println stArray[i];
    def real = bollingScore(stArray[i])
    if( real == expectVal[i] ) {
        println "Testing Case Pass:"+i;
    } else {
        println "Testing Case failure:"+i;
        break;
   }
} 
