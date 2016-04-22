/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Package Declaration
package id3;    

//Importing Libraries
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author KishoreKumar
 */
//Declaration of Main Class "ID3_Main"
public class ID3_Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    //Declaring Main method
        // TODO code application logic here
        //Declaring variables
        String trainingfilepath= "C:\\Users\\KishoreKumar\\Documents\\NetBeansProjects\\ID3\\src\\id3\\training.txt";   //training.txt path
        String validationfilepath="C:\\Users\\KishoreKumar\\Documents\\NetBeansProjects\\ID3\\src\\id3\\validation.txt";//validation.txt path
        //Creating class Objects
        ID3_DataFile datafile = new ID3_DataFile(trainingfilepath); 
        ID3_DataFile validationfile = new ID3_DataFile(validationfilepath);
        //Assigning the class variables with object reference to read data files i.e., training.txt and validation.txt;
        ID3_DataFileRecords training = datafile.readFileData();
        ID3_DataFileRecords validation = validationfile.readFileData();
        training.getRecords(); //Loading records from training.txt file
        training.getAttributes();//Loading Attributes from training.txt file
        ID3_DecisionTree tree = new ID3_DecisionTree(training, 0); //Creating Object reference for ID3_DecisionTree class
	ID3_DecisionTreeClassifier classifier = new ID3_DecisionTreeClassifier(tree, validation); //Creating Object reference for ID3_DecisionTreeClassifier class
        System.out.println("Accuracy is : " + classifier.getAccuracy()); //Outputting the Final accuracy
        ID3_DecisionTree tree1 = new ID3_DecisionTree(training, 7.815); //Creating Object reference for ID3_DecisionTree class
	ID3_DecisionTreeClassifier classifier1 = new ID3_DecisionTreeClassifier(tree1, validation); //Creating Object reference for ID3_DecisionTreeClassifier class
        System.out.println("Accuracy is : " + classifier1.getAccuracy()); //Outputting the Final accuracy
        ID3_DecisionTree tree2 = new ID3_DecisionTree(training, 11.345); //Creating Object reference for ID3_DecisionTree class
	ID3_DecisionTreeClassifier classifier2 = new ID3_DecisionTreeClassifier(tree2, validation); //Creating Object reference for ID3_DecisionTreeClassifier class
        System.out.println("Accuracy is : " + classifier2.getAccuracy()); //Outputting the Final accuracy
        
    }

}

//Declaring ID3_DataFile class; This class is used to load data from files, format and modify that data.
class ID3_DataFile {
    private String dataFile = null; //variable declaration
    public ID3_DataFile(String file) { //User defined constructor declaration used to set DataFile.
        this.setDataFile(file); 
    }

    enum AttributeClass { //Declaring Enumeration to hold promoter and non promoter and their assignments

        PROMOTER("+"), NON_PROMOTER("-");
        private String classVal;

        AttributeClass(String classVal) { //Method to set Class Attributes
            this.classVal = classVal;
        }

        public String getClassValue() { //Method to get the class values
            return this.classVal;
        }
    }
    /**
     * @return the dataFile
     */
    
    //Setter and Getter methods declaration
    public String getDataFile() { //Method to get th data file details
        return dataFile;
    }

    /**
     * @param dataFile the dataFile to set
     */
    public void setDataFile(String dataFile) { // MEthod to set Data File details
        this.dataFile = dataFile;
    }

    private ArrayList getAttributeHeaders() { //Method to get the Attribute Headers
        ArrayList attribute = new ArrayList(); //declaring ArrayList attribute to store attribute headers
        for (int i = 0; i < 57; i++) {
            attribute.add("Attribute - " + (i + 1)); //Assigning the unique attribute headers
        }
        return attribute;
    }

    public ID3_DataFileRecords readFileData() { //Method to read data from file
        BufferedReader br = null; //Declaring BufferedReader method which is used to read data
        ID3_DataFileRecords record = null; //Initializing variables
        try {
            br = new BufferedReader(new FileReader( //Assigning file reference to bufferedreader
                    this.getDataFile())); //getting data file details
            String datarecord = null;
            record = new ID3_DataFileRecords();
            record.setAttributes(getAttributeHeaders()); //Setting Attribute headers
            ArrayList dataSamples = new ArrayList();
            while ((datarecord = br.readLine()) != null) { //Reading the file data line by line
                ArrayList sampleValues = new ArrayList();
                String[] s = datarecord.split(" "); //Splitting each record untill before the space and storing the result(attributes) in a string array.
                char[] attrofrecord = s[0].toCharArray(); //converting each record in to character array
                for (int i = 0; i < attrofrecord.length; i++) {
                    sampleValues.add(attrofrecord[i] + "");//storing the attribute values in an ArrayList
                }
                DataRecord sample = new DataRecord(); //Creating an object for DataRecord class
                sample.setRecordValues(sampleValues); //setting the record values of training data 
                sample.setClassType(s[1]); // Setting the class types
                dataSamples.add(sample); //Adding the data samples to the ArrayList dataSamples
            }
            record.setDataSamples(dataSamples); //Setting up the dataSamples 
            br.close(); //Closing Buffered Reader
        } catch (Exception e) {  } //Catch block to catch the exception if any araise.
        return record; //returning the record to the callind block.
    }
}

//ID3_DataFileRecords class: This class is used for setting and getting the data records and attributes.
class ID3_DataFileRecords {
	private ArrayList<DataRecord> records = new ArrayList<>(); //Declaring an ArrayList to store the data records
	private ArrayList<String> attributes = new ArrayList<>(); //Declaring an ArrayList to store the dataset attributes

	public ArrayList<DataRecord> getRecords() { //Getter method to get the data records
		return records;
	}

	public void setDataSamples(ArrayList<DataRecord> dataSamples) { //Setter method to set the DataSamples
		this.records = dataSamples;
	}

	public ArrayList<String> getAttributes() { //getter method to get the data set attributes
		return attributes;
	}

	public void setAttributes(ArrayList<String> features) { //setter method to set the data set attributes
		this.attributes = features;
	}
}

//DataRecord class: Thi class is used to modify the datasets inorder to achieve the required reault
class DataRecord {
	private ArrayList<String> recordValues = new ArrayList<>();//Declaring the ArrayList to store the modified dataset record values
	private String classType = null;

	public DataRecord(){} //Class default constructor
	
	public DataRecord(DataRecord record) { //Userdefined Class Contructor
		this.recordValues = new ArrayList<>(record.getRecordValues()); //Getting the record values and storing them in to the array list
		this.setClassType(new String(record.getClassType())); //Setting the class types
	}
	
	public void removeRecordValues(int index) { //Method to perform an action to remove the unwanted records from the dataset
		this.recordValues.remove(index);
	}
	
	public String getClassType() { //Getter method to get the class types
		return classType;
	}

	public void setClassType(String classType) { //Setter method to set the class types
		this.classType = classType;
	}

	public ArrayList<String> getRecordValues() { //Getter method to get the record values
		return recordValues;
	}
	
	public void setRecordValues(ArrayList<String> recordValues) { //Setter method to set/return the  dataset record values
		this.recordValues = recordValues;
	}
}

//class ID3_DecisionTree: This class is used to perform operations related to the decision tree
class ID3_DecisionTree {

    //Declaring variables
    private ArrayList indexes;
    public ID3_Node rootnode;
    static Scanner scan = new Scanner(System.in);
    ID3_DataFileRecords collection = null;

    public ArrayList getIndexes() {//getter method to get the Indexes of data set
        return indexes;
    }

    public ID3_DecisionTree(ID3_DataFileRecords sample_collection, double chisquarevalue) {
        //User  defined constructor of class
        this.chisquarevalue = chisquarevalue;
        this.collection = sample_collection; 
        rootnode = ID3(sample_collection.getAttributes(), sample_collection.getRecords()); //Calculating the root node of the tree
        indexes = new ArrayList<>(); //creating the indexes arraylist
        for (int featureIndex = 0; featureIndex < sample_collection.getRecords().size(); featureIndex++) { //Assigning the indexes
            indexes.add(featureIndex);
        }
        System.out.println("Root Node:" + rootnode); //Outputting/printing the Root Node
    }

    private String getProminentClass(List<DataRecord> samples) {//getter method to get the Prominent class data
        int pos = 0, neg = 0;
        for (DataRecord sample : samples) { //calculating the count of positive and negative values of prominent class
            if (ID3_DataFile.AttributeClass.PROMOTER.getClassValue().equals(sample.getClassType())) {
                pos++;
            } 
            else {
                neg++;
            }
        }
        return pos > neg ? ID3_DataFile.AttributeClass.PROMOTER.getClassValue(): ID3_DataFile.AttributeClass.NON_PROMOTER.getClassValue();
    }

    private int getMaxGain(ArrayList attributes, ArrayList records) { //getter method to get the maximum gain value
        //Declaring the variables
        double maxInformationGain = Double.MIN_VALUE;
        int maxGainIndex = 0;
        for (int i = 0; i < attributes.size(); i++) { //Calculating the information gain
            double infoGain = getInformationGain(records, i);
            // System.out.println(maxInformationGain+" "+infoGain);
            if (Double.compare(infoGain, maxInformationGain) > 0) {
                maxInformationGain = infoGain;
                maxGainIndex = i;
            }
        }
        return maxGainIndex;

    }

    double chisquarevalue = 0; //0,7.815, 11.345 -chisquarevalue change this value and observer different absolute values
    double degreeoffreedom = 3; //Degree of freedom for the data given
    
    private ID3_Node ID3(ArrayList<String> attributeList, ArrayList<DataRecord> samples) {//Method to calculate the ID3 algorithm
        ID3_Node tempNode; //varialbe to store temporary node
        if (attributeList.isEmpty()) {
            tempNode = new ID3_Node(true, getProminentClass(samples)); //calculating the temporary node
            return tempNode;
        }
        int bestFeature = getMaxGain(attributeList, samples); //calculating the maximum gain
        Set<String> bestAttrVals = getUniqueVals(bestFeature, samples); //calculating the unique attribute values
        tempNode = new ID3_Node(attributeList.get(bestFeature),
                bestAttrVals.size());
        List<String> subAttributes = new ArrayList<>(attributeList); //List to store the sub-nodes/attribute details
        subAttributes.remove(bestFeature);//removing the unwanted attributes
        bestAttrVals.stream().forEach((String attrVal) -> {
            ArrayList<DataRecord> subset = getSubset(samples, bestFeature, attrVal);
            ID3_Node childNode = ID3(
                    new ArrayList<>(subAttributes), subset);
            if (childNode.isLeafNode()) {
            } else {
                if (addNode(subset)) { //calculating the other nodes
                    
                    Set<String> uniq = getUniqueVals(bestFeature, subset); //calculating unique values
                    double nodePromo = 0, node_nonPromo = 0;
                    double size = subset.size();
                    for (DataRecord sample : subset) {
                        if (sample.getClassType().equals(
                                ID3_DataFile.AttributeClass.PROMOTER
                                        .getClassValue())) {
                            nodePromo++;
                        } else {
                            node_nonPromo++;
                        }
                    }

                    double chi = 0.0;
                    for (String attrib : uniq) {
                        double count[] = getCount(bestFeature, attrib, subset);
                        double sum = (count[0] + count[1]);

                        double divideRes1 = sum * (nodePromo / size);
                        double result1 = Math.pow((count[0] - divideRes1), 2)/ divideRes1;

                        double divideRes2 = sum * (node_nonPromo / size);
                        double result2 = Math.pow((count[1] - divideRes2), 2)/ divideRes2;

                        chi = chi + (result1 + result2); //calculating chi value

                    }
                    if (chi <= chisquarevalue) {
                        ID3_Node tempNode2 = new ID3_Node(true, getProminentClass(subset)); //calculating the temporary node
                        tempNode.setChildNode(attrVal, tempNode2); //setting up the child nodes to te current node
                    }
                    else  tempNode.setChildNode(attrVal, childNode);//setting up the childnode
                    chisquare(samples, subset);//getting the chisquare values
                } else {
                    ID3_Node tempNode2 = new ID3_Node(true,getProminentClass(subset));
                    tempNode.setChildNode(attrVal, tempNode2);
                }
            }
        });
        return tempNode;
    }
    
    private double[] getCount(int bestFeature, String attrib,List<DataRecord> samples) { //calculating the promotional and nonpromotional count
        double promoCnt = 0, non_promoCnt = 0;
        for (DataRecord sample : samples) {
            if (sample.getRecordValues().get(bestFeature).equals(attrib)) {
                if (sample.getClassType().equals(ID3_DataFile.AttributeClass.PROMOTER.getClassValue())) {
                    promoCnt++;
                } else {
                    non_promoCnt++;
                }
            }
        }
        return new double[]{promoCnt, non_promoCnt};
    }

    public boolean addNode(List<DataRecord> childSamples) { //method to add nodes
        int childPromo = 0, child_nonPromo = 0; //declaring the variables
        for (DataRecord sample : childSamples) { 
            if (sample.getClassType().equals(
                    ID3_DataFile.AttributeClass.PROMOTER.getClassValue())) {
                childPromo++;
            } else {
                child_nonPromo++;
            }
        }
        return !(childPromo == 0 || child_nonPromo == 0);
    }

    public void chisquare(List<DataRecord> parentSamples, List<DataRecord> childSamples) { //Method to calculate the chisquare 
        int parentPromo = 0, parent_nonPromo = 0;
        for (DataRecord sample : parentSamples) {
            if (sample.getClassType().equals(
                    ID3_DataFile.AttributeClass.PROMOTER.getClassValue())) {
                parentPromo++;
            } else {
                parent_nonPromo++;
            }
        }
        int childPromo = 0, child_nonPromo = 0;
        for (DataRecord sample : childSamples) {
            if (sample.getClassType().equals(
                    ID3_DataFile.AttributeClass.PROMOTER.getClassValue())) {
                childPromo++;
            } else {
                child_nonPromo++;
            }
        }
    }

    public void chiTest(double[] values, List<DataRecord> parentSamples,List<DataRecord> childSamples, ID3_Node parentNode, ID3_Node childNode, String attrVal, int bestFeature) {
		if (childNode.getAttributeName() == null) {
            return;
        }
        int index = collection.getAttributes().indexOf(childNode.getAttributeName());
		Set<String> bestAttrVals = getUniqueVals(index,collection.getRecords());//getting the best attribute values
		int s = childSamples.size();
                double chi = 0.0;
        for (String attribVals : bestAttrVals) {
            int childPromoterAttribCount = 0, childNon_PromoterAttribCount = 0;
            for (DataRecord parentSample : parentSamples) {
                if (parentSample.getRecordValues().get(bestFeature).equals(attrVal)) {
			    if (parentSample.getClassType().equals(ID3_DataFile.AttributeClass.PROMOTER.getClassValue())) {
				childPromoterAttribCount++;
                    } else {
                        childNon_PromoterAttribCount++;
                    }
                } else {
                    // System.out.println(parentSample.getSampleValues().get(bestFeature)+":"+attrVal);
                }
            }
            double promDivideRes = (values[1] / values[0]);
            double firstHalf = (Math.pow((childPromoterAttribCount - (s * promDivideRes)), 2));
            double non_promDivideRes = (values[2] / values[0]);
            double secondHalf = (Math.pow((childNon_PromoterAttribCount - (s * non_promDivideRes)),2));
            chi = chi + (firstHalf / promDivideRes)+ (secondHalf / non_promDivideRes);
        }
        
    }
    private ArrayList<DataRecord> getSubset(ArrayList<DataRecord> samples, int bestFeature,String attrVal) { //calculating the tree subsets
        ArrayList<DataRecord> subset = new ArrayList<>();
        samples.stream().filter((sample) -> (sample.getRecordValues().get(bestFeature).equals(attrVal))).map((sample) -> new DataRecord(sample)).map((newSample) -> {
            newSample.removeRecordValues(bestFeature);
            return newSample;
        }).forEach((newSample) -> {
            subset.add(newSample);
        });
        return subset;
    }

    private Set<String> getUniqueVals(int bestFeature, List<DataRecord> samples) {//calculating the unique values in an attribute
        Set<String> uniqvals = new HashSet<>();
        samples.stream().forEach((sample) -> {
            uniqvals.add(sample.getRecordValues().get(bestFeature));
        });
        return uniqvals;
    }

    public double getEntropy(List<DataRecord> samples) {//getter method to get the entropy
        double pos = 0, neg = 0;

        for (DataRecord sample : samples) {
            if (ID3_DataFile.AttributeClass.PROMOTER.getClassValue().equals(
                    sample.getClassType())) {
                pos++;
            } else {
                neg++;
            }
        }
        // return getEntropy(pos, neg, samples.size());
        return calculateImpurityEntropy(pos, neg, samples.size());
    }

    public double calculateImpurityEntropy(double a, double b, double size) {//calculating the Impurity Entropy
        double pa = a / size;
        double pb = b / size;

        double res = 0;
        if (a > 0) {
            res += -pa * Math.log(pa);
        }
        if (b > 0) {
            res += -pb * Math.log(pb);
        }

        return res / Math.log(2);
    }

    public double getEntropy(double pos, double neg, int size) { //getter method to calculate the entropy
        double ppos, pneg, ent;
        ppos = pos / size;
        pneg = neg / size;
        if (ppos == 0) {
            ppos = 1;
        }
        if (pneg == 0) {
            pneg = 1;
        }
        ent = -(ppos * (Math.log(ppos) / Math.log(2)))
                - (pneg * (Math.log(pneg) / Math.log(2)));
        return Math.abs(ent);
    }

    public double getInformationGain(List<DataRecord> samples, int featureIndex) {//getter method to calculate the Information Gain
        double entropy = 0.0;
        Set<String> uniquevals = getUniqueVals(featureIndex, samples);//getting the unique vlaues
        for (String val : uniquevals) {
            double pos = 0, neg = 0;
            for (DataRecord sample : samples) {
                if (sample.getRecordValues().get(featureIndex).equals(val)) {
                    if (ID3_DataFile.AttributeClass.PROMOTER.getClassValue().equals(sample.getClassType())) {
                        pos++;
                    } else {
                        neg++;
                    }
                }
            }
            entropy += ((pos + neg) / samples.size())* calculateImpurityEntropy(pos, neg, (pos + neg));
        }
        entropy = getEntropy(samples) - entropy;
        return entropy;
    }
}
//Class of ID3_DecisionTreeClssifier: Used to classify the Decision Tree
class ID3_DecisionTreeClassifier {
//Declaring the variables	
    private double accuracy;
	public double getAccuracy() {
		return accuracy;
	}

	private final ID3_DecisionTree idt;
	public ID3_DecisionTreeClassifier(ID3_DecisionTree idt, ID3_DataFileRecords records) {//user defined constructor
		this.idt = idt;
                AccuracyMeasure(records.getRecords(), records.getAttributes());//calculating the accuracy 
	}

	private void AccuracyMeasure(List<DataRecord> records, List<String> attributes) {//method to calculate the accuracy
		double trues = 0, falses = 0;
		for (DataRecord record : records) {
			String classifiedAs = classification(record, attributes);
			if (classifiedAs.equals(record.getClassType()))
				trues++;
			else
				falses++;
		}
		accuracy = (trues / (trues + falses)) * 100; //Accuracy calcultion
	}

	private String classification(DataRecord record, List<String> attributes) { //method to clasify the data
		ID3_Node root = idt.rootnode;
		while (true) {
			if (!root.isLeafNode()) {
				String attrname = root.getAttributeName();
				int attrindex = attributes.indexOf(attrname);
				String attrvalue = record.getRecordValues().get(attrindex);
				if (root.getChildnodes().keySet().contains(attrvalue)) {
					root = root.getChildnodes().get(attrvalue);
				} else {
					return prominentClass(root);
				}
			} else
				return root.getNodeclass();
		}
	}

	private String prominentClass(ID3_Node rootnode) {//method to calculate the prominent classes
		int prominents = 0, nonprominents = 0;
		Queue<ID3_Node> currentnode = new LinkedBlockingQueue<>();
		currentnode.add(rootnode);
		while (!currentnode.isEmpty()) {
			ID3_Node node = currentnode.poll();
			if (node.isLeafNode()) {
				if (ID3_DataFile.AttributeClass.PROMOTER.getClassValue().equals(
						node.getNodeclass())) { //calculating promoter
					prominents++;
				} else {
					nonprominents++; //calculating non-promoter
				}
			} else {
				HashMap<String, ID3_Node> children = node.getChildnodes();
                                children.keySet().stream().forEach((values) -> {
                                    currentnode.add(children.get(values));
                            });
			}

		}
		return (prominents > nonprominents) ? ID3_DataFile.AttributeClass.PROMOTER.getClassValue() : ID3_DataFile.AttributeClass.NON_PROMOTER.getClassValue();
	}
}

//Class ID3_Node: this class is used to create/modify the nodes
class ID3_Node {
        //declaring the variables
	private String attributeName;
        private boolean isleafnode;
	private String nodeclass;

	private HashMap<String, ID3_Node> childnodes;
	public HashMap<String, ID3_Node> getChildnodes() {
		return childnodes;
	}

	@Override
	public String toString() {
		if (isleafnode)
			return nodeclass;
		return attributeName;
	}

	public void setChildNode(String featureValue, ID3_Node childNode) {//setter method to set the child nodes
		childnodes.put(featureValue, childNode);
	}

	public int getNumChild() {//getter method to get the number of child nodes
		if(this.isleafnode)
			return 0;
		return this.childnodes.size();
	}
        
        private void setNumChild(int numArcs) {//setter method to set the no of childs
		childnodes = new HashMap<>(numArcs);
	}

	ID3_Node(boolean isLeaf, String nodeclass) { //user defined class constructor
		setLeafNode(isLeaf);//setting leaf node
		setNodeclass(nodeclass);//setting node class
	}

	ID3_Node(String featureName, int numArcs) {//user defined constructor
		setAttributeName(featureName);//setting attribute names
		setNumChild(numArcs);//setting number of child nodes
		setLeafNode(false);//setting leaf nodes
	}

	public String getAttributeName() {//getter method to get attribute names
		return attributeName;
	}

	public void setAttributeName(String attributeName) {//setter method to set attribute names
		this.attributeName = attributeName;
	}

	public boolean isLeafNode() { //method to check loaf node or not
		return isleafnode;
	}

	public void setLeafNode(boolean isLeaf) {//setting up the leaf node
		this.isleafnode = isLeaf;
	}

	public String getNodeclass() {//to get the node class
		return nodeclass;
	}

	public void setNodeclass(String nodeclass) { //to set the node class
		this.nodeclass = nodeclass;
	}

}
