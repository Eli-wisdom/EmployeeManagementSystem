
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.coobird.thumbnailator.Thumbnails;
import net.proteanit.sql.DbUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author #Eli
 */
public class empDetail extends javax.swing.JFrame {

   
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    //////initioation
    int idNum;
    
    String adminName;
    String password;
    
    //String addId;
    String addOname;
    String addOpassword;
    String addName;
    String addPassword;
     int see ;
     
     
     String empIName = null;
     String empIAge = null;
     String empTableName = null;
    
    empInput inputt = new empInput();
    
    
    public empDetail() {
       
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
       // update_table();
       // autoRefreash();
        
         conn = connector.connect();
         autoRefreash();
         update_table();
         getLastRecord();
         see = 0;
         
        
    txt_title.setEditable(false);
   txt_salary.setEditable(false);
    txt_qualification.setEditable(false);
    txt_name.setEditable(false);
    txt_id.setEditable(false);
    txt_gender.setEditable(false);
    txt_department.setEditable(false);
    txt_contact.setEditable(false);
    txt_blood.setEditable(false);
    txt_age.setEditable(false);
    txt_address.setEditable(false);
         
    
    }
    
     public void update_table(){
    
    String sql = "select * from employeeinfo2";
        try {
            
    pst = conn.prepareStatement(sql);
    rs=pst.executeQuery();
    
    jTable1.setModel(DbUtils.resultSetToTableModel(rs));
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
            
        }finally{
        
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        
        
        }
   
    
    
    }
      public void Close(){
    WindowEvent wce = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wce);
    
    }
      public void autoRefreash(){
      
      
       try {
            String sql = "select * from employeeinfo2 where EmployeeID =1 ";
            
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()){
            
            String strempID = rs.getString("EmployeeID");
            txt_id.setText(strempID);
            
            String strname = rs.getString("Name");
            txt_name.setText(strname);
            empIName=txt_name.getText();
            
            String strgender =rs.getString("Gender");
            
            if("male".equals(strgender)){
            
            txt_gender.setText("MALE");
            }if("female".equals(strgender)){
            
            txt_gender.setText("FEMALE");
            }
            
            String strage =rs.getString("age");
            txt_age.setText(strage);
           empIAge=txt_age.getText();
           
           empTableName="ept"+empIAge;
         //  field_payment.setText(empTableName);
         
            String strblood = rs.getString("BloodGroup");
            txt_blood.setText(strblood);
            
            String strcon = rs.getString("ContactNo");
            txt_contact.setText(strcon);
            
            String strqual = rs.getString("Qualification");
            txt_qualification.setText(strqual);
            
            String strDep = rs.getString("Department");
            txt_department.setText(strDep);
            
            String strTit = rs.getString("Title");
            txt_title.setText(strTit);
            
            String strSal = rs.getString("Salary");
            txt_salary.setText(strSal);
            
            
            //////
            //  txt_id.setString(txt_id.getText));
             // idNum.getText(txt_id);
              
              idNum=Integer.parseInt(strempID);
              
              
              
              ///
            
            String strdate =rs.getString("DOJ");
          //  java.util.Date sdf = new SimpleDateFormat("yyyy-MM-dd").parse(strdate);
            txt_DOJ.setText(strdate);
            
           String strad = rs.getString("Address");
           txt_address.setText(strad);
           
           byte[] imagedata = rs.getBytes("EmpImage");
           formate = new ImageIcon(imagedata);
           imagelabel.setIcon(formate);
           employeeimage = imagedata;
           
            
           
            }
            
         } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
         }finally{
        
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        
        }
      
      
      
      
      getLastRecord();
      }
      
      public void adminUpdateHand(){
      
          try {
              String sql = "update user set  password = ? where name ='"+addOname+"' ";
              
              pst = conn.prepareStatement(sql);
               
                pst.setString(1, addPassword);
              pst.execute();
               JOptionPane.showMessageDialog(null, "UPDATED");
          } catch (Exception e) {
              
              
              JOptionPane.showMessageDialog(null, "INVALID INPUTS");
        }finally{
        
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        
          }
      
      }
        ////////////COULD BE DELETED/////////////
   public void setAddminUpdateValidation(){
                  try {
            String sql = "select * from user where  name = ? and password = ?";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, addOname);
            pst.setString(2, addOpassword);
            
            rs = pst.executeQuery();

            if (rs.next()) {
              
                JOptionPane.showMessageDialog(null, "USERNAME AND PASSWORD EXIST");
                
                 see = 1;
                //employeeInfo info = new employeeInfo(1,2);
            } else {
                JOptionPane.showMessageDialog(null, "USERNAME AND PASSWORD NOT EXIST");
                see = 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    
    
    }
    public void  findPaymentTable(){
         String numberForPayment =txt_contact.getText();  
         tableName = "apt"+numberForPayment;
     
     
        
        
         System.out.println(dateOutput);
               
            
          getCurrentDateNPay();   
    
    }
     public void getCurrentDateNPay(){
     
     
     try {
            //////////////////////////////////////
            
            
             JOptionPane.showMessageDialog(null,tableName);
             String sql = "insert into  "+tableName  +"(DatePayment	,Amount)values(?,?)   ";
             
             //create table " + tableName + "(ID INT(11) NOT NULL AUTO_INCREMENT KEY,DatePayment Date,amount int(11))
           
                pst = conn.prepareStatement(sql);
                
                Date date = new Date();
               //  Month mo = new Month();
        
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateOutput = sdf.format(date);
               
                pst.setString(1,dateOutput);
              
                pst.setString(2, txt_salary.getText());
             
             
            
             pst.execute();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null," ALREADY BEEN PAID");
           
        }finally {

                try {

                    rs.close();
                    pst.close();

                } catch (Exception e) {
                    //    JOptionPane.showMessageDialog(null, e);
                }

            }
     
     }
     public void getLastRecord(){
         String numberForPayment =txt_contact.getText();  
         tableName = "apt"+numberForPayment;
     
         try {
               // JOptionPane.showMessageDialog(null,tableName);
               String sql = "select  DatePayment from "+ tableName +" ORDER BY DatePayment DESC  ";
               pst = conn.prepareStatement(sql);
               rs = pst.executeQuery();
               if(rs.next()){
               String DatePay = rs.getString("DatePayment");
           //    field_payment.setText(DatePay);
               
               }     
          
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null,e.getMessage());
         }
     
     }
   
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btn_new = new javax.swing.JButton();
        btn_updateTT = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_print = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        imagelabel = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_name = new javax.swing.JTextField();
        txt_gender = new javax.swing.JTextField();
        txt_contact = new javax.swing.JTextField();
        txt_blood = new javax.swing.JTextField();
        txt_qualification = new javax.swing.JTextField();
        txt_address = new javax.swing.JTextField();
        txt_DOJ = new javax.swing.JTextField();
        txt_age = new javax.swing.JTextField();
        txt_department = new javax.swing.JTextField();
        txt_title = new javax.swing.JTextField();
        txt_salary = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_addAdmin = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txt_updateAdmin = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        btn_new.setBackground(new java.awt.Color(0, 0, 0));
        btn_new.setText("NEW");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });

        btn_updateTT.setText("UPDATE");
        btn_updateTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateTTActionPerformed(evt);
            }
        });

        btn_delete.setText("DELETE");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_print.setText("PRINT");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_new, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_updateTT, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
            .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(btn_new, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_updateTT, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setBackground(new java.awt.Color(0, 204, 204));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jDesktopPane1.setBackground(new java.awt.Color(0, 102, 102));

        jDesktopPane1.setLayer(imagelabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txt_id.setBackground(new java.awt.Color(0, 102, 102));
        txt_id.setFont(new java.awt.Font("Arial", 0, 35)); // NOI18N
        txt_id.setForeground(new java.awt.Color(0, 255, 255));
        txt_id.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        txt_name.setBackground(new java.awt.Color(0, 102, 102));
        txt_name.setFont(new java.awt.Font("Arial", 0, 28)); // NOI18N
        txt_name.setForeground(new java.awt.Color(255, 255, 255));
        txt_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        txt_gender.setBackground(new java.awt.Color(0, 102, 102));
        txt_gender.setFont(new java.awt.Font("Arial", 0, 35)); // NOI18N
        txt_gender.setForeground(new java.awt.Color(0, 255, 255));
        txt_gender.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        txt_contact.setBackground(new java.awt.Color(0, 102, 102));
        txt_contact.setFont(new java.awt.Font("Arial", 0, 35)); // NOI18N
        txt_contact.setForeground(new java.awt.Color(0, 255, 255));
        txt_contact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        txt_blood.setBackground(new java.awt.Color(0, 102, 102));
        txt_blood.setFont(new java.awt.Font("Arial", 0, 35)); // NOI18N
        txt_blood.setForeground(new java.awt.Color(0, 255, 255));
        txt_blood.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        txt_blood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bloodActionPerformed(evt);
            }
        });

        txt_qualification.setBackground(new java.awt.Color(0, 102, 102));
        txt_qualification.setFont(new java.awt.Font("Arial", 0, 35)); // NOI18N
        txt_qualification.setForeground(new java.awt.Color(255, 255, 255));
        txt_qualification.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        txt_address.setBackground(new java.awt.Color(0, 102, 102));
        txt_address.setFont(new java.awt.Font("Arial", 0, 35)); // NOI18N
        txt_address.setForeground(new java.awt.Color(0, 255, 255));
        txt_address.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        txt_DOJ.setBackground(new java.awt.Color(0, 102, 102));
        txt_DOJ.setFont(new java.awt.Font("Arial", 0, 35)); // NOI18N
        txt_DOJ.setForeground(new java.awt.Color(0, 255, 255));
        txt_DOJ.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        txt_DOJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DOJActionPerformed(evt);
            }
        });

        txt_age.setBackground(new java.awt.Color(0, 102, 102));
        txt_age.setFont(new java.awt.Font("Arial", 0, 35)); // NOI18N
        txt_age.setForeground(new java.awt.Color(0, 255, 255));
        txt_age.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        txt_department.setBackground(new java.awt.Color(0, 102, 102));
        txt_department.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        txt_department.setForeground(new java.awt.Color(153, 153, 0));
        txt_department.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        txt_title.setBackground(new java.awt.Color(0, 102, 102));
        txt_title.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        txt_title.setForeground(new java.awt.Color(204, 204, 0));
        txt_title.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        txt_salary.setBackground(new java.awt.Color(0, 102, 102));
        txt_salary.setFont(new java.awt.Font("Arial", 2, 30)); // NOI18N
        txt_salary.setForeground(new java.awt.Color(204, 204, 0));
        txt_salary.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel1.setText("USER ID");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel2.setText("USER NAME");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel3.setText("CONTACT");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel4.setText("GENDER");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel5.setText("AGE");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel6.setText("BLOODGROUP");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel7.setText("QUALIFICATION");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel8.setText("ADDRESS");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel9.setText("TITLE");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel10.setText("SALARY");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel11.setText("DEPARTMENT");

        jLabel12.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel12.setText("DATE");

        txt_addAdmin.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        txt_addAdmin.setText("ADD");
        txt_addAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_addAdminActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(255, 255, 0));

        txt_updateAdmin.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        txt_updateAdmin.setText("UPDATE");
        txt_updateAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_updateAdminActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText(" ADMINISTRATOR");

        jButton1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jButton1.setText("DELETE");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 51));
        jButton2.setText("PAY SALARY");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_addAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_updateAdmin)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_id, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(txt_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(40, 40, 40)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5)
                                            .addComponent(txt_age, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(76, 76, 76))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_contact)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel12)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_qualification, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                .addComponent(txt_address, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                .addComponent(txt_DOJ, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                                .addComponent(txt_blood, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_department, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_title)
                                            .addComponent(txt_salary, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel10))
                                    .addGap(112, 112, 112))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(240, 240, 240)))))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_addAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_updateAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(95, 95, 95))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_blood, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_qualification, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_DOJ, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8)
                                        .addGap(12, 12, 12))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_department, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(61, 61, 61)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_title, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_salary, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_age, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_address, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
     
    
    
    
    private void btn_updateTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateTTActionPerformed
       // inputt.btn_update.setVisible(true);
       empInput empUpdate = new empInput();
        empUpdate.setVisible(true);
        empUpdate. updateHand(idNum);
        
        dispose();
       
    }//GEN-LAST:event_btn_updateTTActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
        
          int row = jTable1.getSelectedRow();
        String table_click = (jTable1.getModel().getValueAt(row,0).toString());
        
        
        try {
            String sql = "select * from employeeinfo2 where EmployeeID ='"+table_click+"' ";
            
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()){
            
            String strempID = rs.getString("EmployeeID");
            txt_id.setText(strempID);
            
            String strname = rs.getString("Name");
            txt_name.setText(strname);
            
            String strgender =rs.getString("Gender");
            
            if(("MALE".equalsIgnoreCase(strgender)) ){
            
            txt_gender.setText("MALE");
            }if("FEMALE".equalsIgnoreCase(strgender)){
            
            txt_gender.setText("FEMALE");
            }
            
            String strage =rs.getString("age");
            txt_age.setText(strage);
            
            String strblood = rs.getString("BloodGroup");
            txt_blood.setText(strblood);
            
            String strcon = rs.getString("ContactNo");
            txt_contact.setText(strcon);
            
            String strqual = rs.getString("Qualification");
            txt_qualification.setText(strqual);
            
            String strDep = rs.getString("Department");
            txt_department.setText(strDep);
            
            String strTit = rs.getString("Title");
            txt_title.setText(strTit);
            
            String strSal = rs.getString("Salary");
            txt_salary.setText(strSal);
            
            
            //////
            //  txt_id.setString(txt_id.getText));
             // idNum.getText(txt_id);
              
              idNum=Integer.parseInt(strempID);
              
              
              
              ///
            
            String strdate =rs.getString("DOJ");
          //  java.util.Date sdf = new SimpleDateFormat("yyyy-MM-dd").parse(strdate);
            txt_DOJ.setText(strdate);
            
           String strad = rs.getString("Address");
           txt_address.setText(strad);
           
           byte[] imagedata = rs.getBytes("EmpImage");
           formate = new ImageIcon(imagedata);
           imagelabel.setIcon(formate);
           employeeimage = imagedata;
           
           getLastRecord();
           
            }
            
         } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
         }finally{
        
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        
        }
        
        
      // btn_save.setEnabled(false);
         btn_new.setEnabled(true);
         btn_updateTT.setEnabled(true);
         btn_delete.setEnabled(true);
        // btn_clear.setEnabled(true); 
        


    }//GEN-LAST:event_jTable1MouseClicked

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        
        
        inputt.setVisible(true);
        
        dispose();
        
        
    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        
        
        if(txt_id.getText().equals("")){
        
            JOptionPane.showMessageDialog(null,"PLEASE SELECT THE DATA TO BE DELETED");
        
        }else{
        
        int p = JOptionPane.showConfirmDialog(this,"DO YOU REALLY WANT TO DELETE", "DELETE", JOptionPane.YES_NO_OPTION);
        
        
                if(p == 0){
                
                            try {
                        
                                String sql ="delete from employeeinfo2 where EmployeeID = ?";
                                pst = conn.prepareStatement(sql);
                                pst.setString(1,txt_id.getText());
                                pst.execute();
                                JOptionPane.showMessageDialog(null,"DELETE SUCCESSFUL");
                                
                    } catch (Exception e) {
                        
                        JOptionPane.showMessageDialog(null,e);
                    }finally{
                            
                                try {
                                    rs.close();
                                    pst.close();
                                } catch (Exception e) {
                                }
                            
                            
                            }
                            
                                
              
                }
        
        }
        
        update_table();
       /* empDetail empD = new empDetail();
        empD.setVisible(true);*/
       update_table();
        
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
              
        MessageFormat header = new MessageFormat("Employee list Report print");
        MessageFormat footer = new MessageFormat("page {0,number,integer}");
        
        try {
            jTable1.print(JTable.PrintMode.FIT_WIDTH,header,footer);
            
        } catch (java.awt.print.PrinterException e) {
            System.err.format("CANNOT PRINT %s%n", e.getMessage());
        }
    }//GEN-LAST:event_btn_printActionPerformed

    private void txt_DOJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DOJActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DOJActionPerformed

    private void txt_bloodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bloodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bloodActionPerformed

    private void txt_addAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_addAdminActionPerformed
        
      adminName =   JOptionPane.showInputDialog("ENTER THE ADMINISTRATOR FIRST NAME");
      password =    JOptionPane.showInputDialog("ENTER THE ADMINISTRATOR PASSWORD");
      
       String sql ="insert into user (name,password)values(?,?)";
        try {
           
            
             pst = conn.prepareStatement(sql);

                pst.setString(1, adminName);

                pst.setString(2, password);
                 pst.execute();
                JOptionPane.showMessageDialog(null,"ADMIN SAVES");
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
            } finally {

                try {

                    rs.close();
                    pst.close();

                } catch (Exception e) {
                    //    JOptionPane.showMessageDialog(null, e);
                }
        }
        
    }//GEN-LAST:event_txt_addAdminActionPerformed

    private void txt_updateAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_updateAdminActionPerformed


     //addId =    JOptionPane.showInputDialog("ENTER THE ADMINISTRATOR'S ID");
     addOname =    JOptionPane.showInputDialog("ENTER THE ADMINISTRATOR'S FIRST NAME");
     addOpassword =    JOptionPane.showInputDialog("ENTER THE ADMINISTRATOR'S PASSWORD");
     
     setAddminUpdateValidation();
     
     if(see == 1){
    // addName =   JOptionPane.showInputDialog("ENTER THE ADMINISTRATOR'S NEW NAME");
     addPassword =JOptionPane.showInputDialog("ENTER THE ADMINISTRATOR'S NEW PASSWORD");
        adminUpdateHand();
     
     }
      
       
        
        
        
        
    }//GEN-LAST:event_txt_updateAdminActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        addOname =    JOptionPane.showInputDialog("ENTER THE ADMINISTRATOR'S  FIRST NAME");
     addOpassword =    JOptionPane.showInputDialog("ENTER THE ADMINISTRATOR'S PASSWORD");
     
     setAddminUpdateValidation();
     
     if(see==1){
     try {
                        
                                String sql ="delete from user where name =?";
                                pst = conn.prepareStatement(sql);
                                pst.setString(1,addOname);
                                pst.execute();
                                JOptionPane.showMessageDialog(null,"DELETE SUCCESSFUL");
                                
                                } catch (Exception e) {

                                    JOptionPane.showMessageDialog(null,e);
                                }finally{
                            
                                try {
                                    rs.close();
                                    pst.close();
                                } catch (Exception e) {
                                }
                            
                            
                            }
     }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      findPaymentTable();
    //  getCurrentDateNPay(){}
        
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(empDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(empDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(empDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(empDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new empDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_new;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_updateTT;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel imagelabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txt_DOJ;
    private javax.swing.JButton txt_addAdmin;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_age;
    private javax.swing.JTextField txt_blood;
    private javax.swing.JTextField txt_contact;
    private javax.swing.JTextField txt_department;
    private javax.swing.JTextField txt_gender;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_qualification;
    private javax.swing.JTextField txt_salary;
    private javax.swing.JTextField txt_title;
    private javax.swing.JButton txt_updateAdmin;
    // End of variables declaration//GEN-END:variables

   

    
String filename = null;
    private ImageIcon Viewimage = null;
    private String gender;
    byte[] employeeimage = null;
    private ImageIcon formate = null;
   String tableName = null;
   String dateOutput = null;
}
