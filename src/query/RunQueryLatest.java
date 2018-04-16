package query;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import engine.QueryEngine;

import spatialindex.quadtree.QuadTree;
import spatialindex.spatialindex.Region;
import storage.invertedindex.FirstLevelInvertedIndex;
import storage.invertedindex.HilbertQgramTokenInvertedIndex;
import storage.invertedindex.InfrequentPositionalQgramInvertedIndex;
import storage.invertedindex.InfrequentQgramTokenInvertedIndex;
import storage.invertedindex.QgramTokenCountPairInvertedIndex;
import storage.invertedindex.SecondLevelInvertedIndex;
import storage.objectindex.SpatialObjectDatabase;
import unit.SpatialObject;
import unit.StopWatch;
import unit.query.QueryType;
import unit.query.SpatialQuery;


public class RunQueryLatest
{

  public static void main( String s ) throws IOException
  {
    // TODO Auto-generated method stub
      
// index query.txt index index 2 3 9 10 5 5 10 0.01 0.0025 2 0
   

    String dataFile = "index";
    String queryFile = s;
    String infrequentFile = "index";
    String invertedIndexFile = "index";
    
    int smallQValue = 2;
    int largeQValue = 3;
    int positionUpperBound = 9;       
    
    int sparseThreshold = 10;
    int selectivityThreshold = 5;
    int resultSizeThreshold = 5;
    int visitingNodeSizeThreshold = 10;
  
    
    double scarceThreshold = 0.01;
    double rangeRadius = 0.0025;
    double areaEnlargedRatio = 2;
    int queryType = 0;

       
           
    System.out.println( "loading indexes" );
    System.out.println( dataFile );

   // load spatial object database
   SpatialObjectDatabase objectDatabase =  new SpatialObjectDatabase( dataFile, smallQValue, largeQValue, positionUpperBound );
   objectDatabase.load();

   // load quad tree
   System.out.println( "loading quad tree from file" );
   QuadTree quadTree = new QuadTree();
   quadTree = quadTree.load( invertedIndexFile );
   
   
   // load infrequent inverted database
   InfrequentPositionalQgramInvertedIndex infrequentInvertedIndex =  new InfrequentPositionalQgramInvertedIndex( infrequentFile, smallQValue );
   infrequentInvertedIndex.loadTree();

   InfrequentQgramTokenInvertedIndex infrequentTokenInvertedIndex =  new InfrequentQgramTokenInvertedIndex( infrequentFile );
   infrequentTokenInvertedIndex.loadTree();


   // load inverted indexes
   FirstLevelInvertedIndex firstLevelInvertedIndex =  new FirstLevelInvertedIndex( invertedIndexFile, smallQValue );
   firstLevelInvertedIndex.loadTree();

   SecondLevelInvertedIndex secondLevelInvertedIndex =  new SecondLevelInvertedIndex( invertedIndexFile, smallQValue );
   secondLevelInvertedIndex.loadMap();
   
   QgramTokenCountPairInvertedIndex qgramTokenCountPairInvertedIndex =  new QgramTokenCountPairInvertedIndex( invertedIndexFile );
   qgramTokenCountPairInvertedIndex.loadTree();

   HilbertQgramTokenInvertedIndex hilbertQgramTokenInvertedIndex = new HilbertQgramTokenInvertedIndex( invertedIndexFile );
   hilbertQgramTokenInvertedIndex.loadTree();

   QueryEngine engine = new QueryEngine( 
     quadTree, objectDatabase, 
     firstLevelInvertedIndex, secondLevelInvertedIndex, 
     infrequentInvertedIndex, infrequentTokenInvertedIndex, 
     qgramTokenCountPairInvertedIndex, hilbertQgramTokenInvertedIndex,
     sparseThreshold, 
     selectivityThreshold, resultSizeThreshold,
     visitingNodeSizeThreshold, 
     scarceThreshold, areaEnlargedRatio, positionUpperBound);

   StopWatch stopWatch = new StopWatch();
   
   LineNumberReader queryReader = new LineNumberReader(new FileReader(queryFile));

   String queryLine = queryReader.readLine();
   System.out.println(queryLine);
   String[] block = null;
   double lat; 
   double lng;
   String word;
   int count = 1;
   
   while( queryLine != null )
   {     
     // initialize a query given a point and a query range
     block = queryLine.split( "_" );
     int id = Integer.parseInt( block[0] );
     lat = Double.parseDouble( block[1] );
     lng = Double.parseDouble( block[2] );
     word = block[3];
          
     double[] lowCood = new double [2];     
     lowCood[0] = lng - rangeRadius;
     lowCood[1] = lat - rangeRadius;
     
     double[] highCood = new double [2];     
     highCood[0] = lng + rangeRadius;
     highCood[1] = lat + rangeRadius;
          
     Region queryRegion = new Region( lowCood, highCood );     
     
     
     SpatialQuery sq = new SpatialQuery( QueryType.PREFIX_RANGE, word, queryRegion, smallQValue, largeQValue, positionUpperBound );
     sq.id = id;
     HashMap< Integer, SimpleEntry < QueryType, SpatialObject > > resultMap;
     
     switch( queryType )
     {
       // query all
       case 0:
    	 resultMap = engine.query( sq, stopWatch );
         break;
         
       default:
    	 resultMap= engine.query( sq, stopWatch );
     }
     
     engine.printResult(sq,resultMap.keySet());
     queryLine = queryReader.readLine();          
     
     
     if ( count % 500 == 0 )
     {
       System.out.println("processing " + count + " / 1000" ); 
     }
     count ++;
   }
   
   queryReader.close();
   System.out.println(stopWatch);         
  }


}
