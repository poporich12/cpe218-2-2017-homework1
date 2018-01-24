
import java.util.Stack;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;


 class Node {
 
    char value;
    int number;
    Node left,right,pre;

    Node(char item) {
        value = item;
        number=Character.getNumericValue(item);
        pre = left = right = null;
        
    }
    
    Node(int num){    
    number=num;}
    
}

public class Homework1 { 
    
    public  boolean isOperator(char c) {
        if (c == '+' || c == '-'
                || c == '*' || c == '/'
                || c == '^') {
            return true;
        }
        return false;
    } 
    static int sum=0; 
    // Utility function to do inorder traversal

 
    // Returns root of constructed tree for given
    // postfix expression
    Node constructTree(char postfix[]) { //???????????? method calculator ?????
        Stack<Node> st = new Stack();
        Stack<Node> stcal =new Stack();
        Node t, t1, t2;
        Node a,a1,a2;
        
        // Traverse through every character of
        // input expression
        for (int i = 0; i < postfix.length; i++) {
 
            // If operand, simply push into stack
            if (!isOperator(postfix[i])) {
                t = new Node(postfix[i]);
                st.push(t);
                stcal.push(t); // ?????????????????????
            } else // operator
            {               
                t = new Node(postfix[i]);
 
                // Pop two top nodes
                // Store top
                t1 = st.pop();      // Remove top
                t2 = st.pop();
 
                //  make them children
                
                
                
                t.right = t1;
                t.left = t2;     
                
                t.right.pre=t;
                t.left.pre=t;
              
                // System.out.println(t1 + "" + t2);
                // Add this subexpression to stack
                st.push(t);
                
                //cal
               a2=stcal.pop();
               a1=stcal.pop();
               
            if(t.value=='+') sum=a1.number+a2.number;
            else if(t.value=='-') sum=a1.number-a2.number;
            else if(t.value=='*') sum=a1.number*a2.number;
            else if(t.value=='/') sum=a1.number/a2.number;
            
            a=new Node(sum);
            stcal.push(a);
            }
        } 
        //  only element will be root of expression
        // tree
        t = st.peek();
        st.pop();
 
        return t;
    }
    

    public static void main(String[] args) {
		// Begin of arguments input sample
	
        JFrame myFrame = new JFrame ( );
        myFrame.setLayout( null );         
                
        Homework1 et = new Homework1();
        char[] charArray = args[0].toCharArray();
        
        Node root = et.constructTree(charArray);
      
    //    et.StrBuf.append(Integer.toString(sum));
        
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				gui frame = new gui(et,root);
				frame.setVisible(true);
			}
		});
                     
         
    }

}


class gui  extends JFrame{
    
//    DefaultMutableTreeNode keep=new DefaultMutableTreeNode();
//    DefaultMutableTreeNode MyForm;
      
 StringBuffer StrBuf= new StringBuffer();
 
 public  boolean isOperator(char c) {
        if (c == '+' || c == '-'
                || c == '*' || c == '/'
                || c == '^') {
            return true;
        }
        return false;
    } 
    
    void inorder(DefaultMutableTreeNode t){//?????????????????? inorder(right) ??????? infix
        char[] value=t.toString().toCharArray();
        if (isOperator(value[0])) {            
            inorder((DefaultMutableTreeNode)t.getChildAt(0));           
            infix(t);
            
        }
    }
 
    void infix(DefaultMutableTreeNode t){    
        
        char[] value=t.toString().toCharArray();
        char[] valueleft= t.getChildAt(0).toString().toCharArray();
        char[] valueright= t.getChildAt(1).toString().toCharArray();
        DefaultMutableTreeNode root=(DefaultMutableTreeNode)t.getRoot();
        
        if(t.getLevel()==0&&!isOperator(valueleft[0])&&!isOperator(valueright[0])){//root
              StrBuf.append(valueleft[0]); 
              StrBuf.append(value[0]);                
              StrBuf.append(valueright[0]);        
        }        
        else if(!isOperator(valueleft[0])&&!isOperator(valueright[0])){ //???????????????????????
                StrBuf.append("("+valueleft[0]); 
                StrBuf.append(value[0]);                
                StrBuf.append(valueright[0]+")");                 
            }        
         else if(t.getPreviousNode()!=null&&!isOperator(valueleft[0])&&isOperator(valueright[0])){ //??????????????????????????????Operator
             StrBuf.append("("+valueleft[0]);                                       //??????????????? root
             StrBuf.append(value[0]);   
             inorder((DefaultMutableTreeNode)t.getChildAt(1));
             StrBuf.append(")");                    
         } 
         else if(isOperator(valueright[0])){ //???????????????????????????????? Operator
             if(!isOperator(valueleft[0])) StrBuf.append(valueleft[0]);
             StrBuf.append(value[0]);
             inorder((DefaultMutableTreeNode)t.getChildAt(1));         
         }
        
          else if(isOperator(valueleft[0])&&!isOperator(valueleft[0])){
               
             StrBuf.append("("+value[0]);   
             StrBuf.append(valueright[0]+")");
         
          }
           else if(!isOperator(valueleft[0])){ //????????????????????
                StrBuf.append(valueleft[0]);
                StrBuf.append(value[0]);
            }        
        
         else if(isOperator(valueleft[0])){//????????????????? Operator
             StrBuf.append(value[0]);   
             StrBuf.append(valueright[0]);  }
            
    }
    
     int cal(DefaultMutableTreeNode r){
         char[] value=r.toString().toCharArray();
         char[] valueleft= r.getChildAt(0).toString().toCharArray();
         char[] valueright= r.getChildAt(1).toString().toCharArray();
     
     if(r!=null){
     if(value[0]=='+') 
            return cal((DefaultMutableTreeNode )r.getChildAt(0)) + cal((DefaultMutableTreeNode )r.getChildAt(1));
     else if(value[0]=='-') 
            return cal((DefaultMutableTreeNode )r.getChildAt(0)) - cal((DefaultMutableTreeNode )r.getChildAt(1));
     else if(value[0]=='*') 
            return cal((DefaultMutableTreeNode )r.getChildAt(0)) * cal((DefaultMutableTreeNode )r.getChildAt(1));
     else if(value[0]=='/') 
            return cal((DefaultMutableTreeNode )r.getChildAt(0)) / cal((DefaultMutableTreeNode )r.getChildAt(1));   
     else      
         return Character.getNumericValue(value[0]);
     }
     return 0;
       
     
     }
    
  

    
    
    
    
    DefaultMutableTreeNode inorderII(Node r){
        DefaultMutableTreeNode item = new DefaultMutableTreeNode(r.value);
        if(r!=null && r.left!=null && r.right!=null){
            item.add(inorderII(r.left));
            item.add(inorderII(r.right));
        }
        return item;
    }
    
    public gui(Homework1 et,Node t) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 362, 249);
		setTitle("ThaiCreate.Com Java GUI Tutorial");
		getContentPane().setLayout(null);               
		// Tree
                DefaultMutableTreeNode A=new DefaultMutableTreeNode(t.value);
                        A.add(inorderII(t));
		final JTree tree = new JTree(A);
		tree.setBounds(28, 11, 209, 131);

		// Scroll Pane
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(28, 11, 209, 169);

		// Image Icon
		//ImageIcon imageIcon = new ImageIcon(getClass().getResource("open.gif"));
		
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	//	renderer.setLeafIcon(imageIcon);

		tree.setCellRenderer(renderer);
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);

		// Add Scroll and Tree
		scroll.setViewportView(tree);
		getContentPane().add(scroll);

		// Selected
		tree.getSelectionModel().addTreeSelectionListener(
				new TreeSelectionListener() {
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						int sum;
                                                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
								.getLastSelectedPathComponent();                                                  
                                               char[] value=selectedNode.toString().toCharArray();
                                                if(isOperator(value[0])){
                                                
                                                inorder(selectedNode);  
                                             //   sum=cal(selectedNode);
                                             //   StrBuf.append("="+Integer.toString(sum));
                                                
						JOptionPane.showMessageDialog(null,StrBuf);
                                                }
                                                else{
                                                JOptionPane.showMessageDialog(null,value[0]);
                                                
                                                }
					}
				});

	}
    
    
    
    
    
}




