import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import com.mysql.jdbc.UpdatableResultSet;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.AreaAveragingScaleFilter;
import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class tpbdd extends JFrame{

	private JFrame frame;
	private JTextField matriculefield;
	private JTextField nomfield;
	private JTextField prenomfield;
	private JTextField sectionfield;
	private JTextField groupefield;
	
	Connection conx=null; 
	PreparedStatement prepared =null;
	ResultSet resultat=null;
	private JTable table;
	JScrollPane scrollPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tpbdd window = new tpbdd();
					window.frame.setVisible(true);
					window.UpdateTable();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public tpbdd() {
		initialize();
		
		 conx = ConnSql.connectdb();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Arial Black", Font.ITALIC, 17));
		frame.getContentPane().setBackground(new Color(0, 204, 204));
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\DELL\\Downloads\\USTHB.jpg"));
		lblNewLabel.setBounds(461, 11, 286, 127);
		frame.getContentPane().add(lblNewLabel);
	
		
		matriculefield = new JTextField();
		matriculefield.setBounds(125, 46, 121, 20);
		frame.getContentPane().add(matriculefield);
		matriculefield.setColumns(10);
		
		nomfield = new JTextField();
		nomfield.setBounds(125, 75, 121, 20);
		frame.getContentPane().add(nomfield);
		nomfield.setColumns(10);
		
		prenomfield = new JTextField();
		prenomfield.setColumns(10);
		prenomfield.setBounds(125, 106, 121, 20);
		frame.getContentPane().add(prenomfield);
		
		sectionfield = new JTextField();
		sectionfield.setColumns(10);
		sectionfield.setBounds(125, 137, 121, 20);
		frame.getContentPane().add(sectionfield);
		
		groupefield = new JTextField();
		groupefield.setColumns(10);
		groupefield.setBounds(125, 168, 121, 20);
		frame.getContentPane().add(groupefield);
		
		JLabel lblMatricule = new JLabel("Matricule :");
		lblMatricule.setFont(new Font("Arial Black", Font.ITALIC, 17));
		lblMatricule.setBounds(10, 46, 105, 20);
		frame.getContentPane().add(lblMatricule);
		
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setFont(new Font("Arial Black", Font.ITALIC, 17));
		lblNom.setBounds(10, 78, 82, 14);
		frame.getContentPane().add(lblNom);
		
		JLabel lblPrenom = new JLabel("Prenom  :");
		lblPrenom.setFont(new Font("Arial Black", Font.ITALIC, 17));
		lblPrenom.setBounds(10, 106, 121, 20);
		frame.getContentPane().add(lblPrenom);
		
		JLabel lblGroupe = new JLabel("Groupe :");
		lblGroupe.setFont(new Font("Arial Black", Font.ITALIC, 17));
		lblGroupe.setBounds(10, 168, 105, 20);
		frame.getContentPane().add(lblGroupe);
		
		JLabel lblSection = new JLabel("Section :");
		lblSection.setFont(new Font("Arial Black", Font.ITALIC, 17));
		lblSection.setBounds(10, 130, 105, 33);
		frame.getContentPane().add(lblSection);
		
		JButton btnAjouter = new JButton("ajouter");
		btnAjouter.setForeground(new Color(255, 102, 255));
		btnAjouter.setIcon(new ImageIcon("C:\\Users\\DELL\\Downloads\\Sign-Add-icon.png"));
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String matricule =matriculefield.getText().toString();
			    String nom= nomfield.getText().toString();
			    String prenom=prenomfield.getText().toString();
			    String section=sectionfield.getText().toString();
			    String groupe=groupefield.getText().toString();
				String sql = " Insert into etudiant (matricule, nom ,prenom ,section,groupe) values (?,?,?,?,?)";
				
				try {
					
					if(!(matricule.equals("") || nom.equals("") || prenom.equals("") || section.equals("") || groupe.equals("")))
					
					{	 
					 prepared = conx.prepareStatement(sql);
					 prepared.setString(1,matricule);
					 prepared.setString(2,nom);
					 prepared.setString(3,prenom);
					 prepared.setString(4,section);
					 prepared.setString(5,groupe);
					 prepared.execute();
					 
					 UpdateTable();
					 
					 matriculefield.setText("");
					 nomfield.setText("");
					 prenomfield.setText("");
					 sectionfield.setText("");
					 groupefield.setText("");
					 
					 JOptionPane.showMessageDialog(null," Etudiant Ajouté avec Succés !!");
					 
					 
					}
					else {
						 JOptionPane.showMessageDialog(null," Remplir tous les champs pour SVP");

						
					}
					 
					
					
				} catch (SQLException e2) {
					// TODO: handle exception
				}
				
			}
		});
		btnAjouter.setBounds(20, 456, 226, 202);
		frame.getContentPane().add(btnAjouter);
		
		JButton btnSupprimer = new JButton("supprimer");
		btnSupprimer.setIcon(new ImageIcon("C:\\Users\\DELL\\Downloads\\Button-Delete-icon.png"));
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int ligne=table.getSelectedRow();
				String matricule= table.getModel().getValueAt(ligne,0).toString();
				
				
				String sql = " Delete from etudiant where matricule ="+matricule+"";
				try {
					
				
					
						 
					 prepared = conx.prepareStatement(sql);
					 
					 prepared.execute();
					 
					 UpdateTable();
					 
					 matriculefield.setText("");
					 nomfield.setText("");
					 prenomfield.setText("");
					 sectionfield.setText("");
					 groupefield.setText("");
					 
					 JOptionPane.showMessageDialog(null," Etudiant Supprimé avec Succés !!");
					 
					 
					
				

						
					
					 
					
					
				} catch (SQLException e2) {
					// TODO: handle exception
				}
				

			
				
			}
		});
		btnSupprimer.setBounds(632, 456, 211, 202);
		frame.getContentPane().add(btnSupprimer);
		
		JButton btnRefresh = new JButton("Actualiser");
		btnRefresh.setIcon(new ImageIcon("C:\\Users\\DELL\\Downloads\\images.jpeg"));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateTable();
				 matriculefield.setText("");
				 nomfield.setText("");
				 prenomfield.setText("");
				 sectionfield.setText("");
				 groupefield.setText("");
			}
		});
		
		btnRefresh.setBounds(20, 203, 182, 226);
		frame.getContentPane().add(btnRefresh);
		
		
		table = new JTable();
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	int ligne=table.getSelectedRow();
				String matricule= table.getModel().getValueAt(ligne,0).toString();
				
				 matriculefield.setText(table.getModel().getValueAt(ligne,0).toString());
				 nomfield.setText      (table.getModel().getValueAt(ligne,1).toString());
				 prenomfield.setText   (table.getModel().getValueAt(ligne,2).toString());
				 sectionfield.setText  (table.getModel().getValueAt(ligne,3).toString());
				 groupefield.setText   (table.getModel().getValueAt(ligne,4).toString());
	        }
	    });
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(300, 149, 595, 280);
		frame.getContentPane().add(scrollPane);
		
		table.setBounds(387, 77, 216, 115);
		frame.getContentPane().add(scrollPane);
		
		JButton btnModifier = new JButton("modifier");
		btnModifier.setBounds(337, 461, 200, 193);
		frame.getContentPane().add(btnModifier);
		btnModifier.setIcon(new ImageIcon("C:\\Users\\DELL\\Desktop\\aa.jpg"));
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int ligne=table.getSelectedRow();
				String matricule= table.getModel().getValueAt(ligne,0).toString();
				
				
				String sql = " Update etudiant set  nom = ? ,prenom= ?  ,section = ? ,groupe= ? where matricule ="+matricule+"";
try {
					
					if(!(nomfield.getText().equals("") || prenomfield.getText().equals("") || sectionfield.getText().equals("") || groupefield.getText().equals("")))
					
					{	 
					 prepared = conx.prepareStatement(sql);
					 
					 prepared.setString(1,nomfield.getText());
					 prepared.setString(2,prenomfield.getText());
					 prepared.setString(3,sectionfield.getText());
					 prepared.setString(4,groupefield.getText());
					 prepared.execute();
					 
					 UpdateTable();
					 
					 matriculefield.setText("");
					 nomfield.setText("");
					 prenomfield.setText("");
					 sectionfield.setText("");
					 groupefield.setText("");
					 
					 JOptionPane.showMessageDialog(null," Etudiant Modifié avec Succés !!");
					 
					 
					}
					else {
						 JOptionPane.showMessageDialog(null," Remplir tous les champs pour SVP");

						
					}
					 
					
					
				} catch (SQLException e2) {
					// TODO: handle exception
				}
				

			}
		});
		frame.setForeground(Color.RED);
		frame.setBounds(100, 100, 1001, 707);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

	public void UpdateTable() {
		// TODO Auto-generated method stub
		String sql="Select * from etudiant"; 
		try {
			table = new JTable();
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
		        public void valueChanged(ListSelectionEvent event) {
		        	int ligne=table.getSelectedRow();
					String matricule= table.getModel().getValueAt(ligne,0).toString();
					
					 matriculefield.setText(table.getModel().getValueAt(ligne,0).toString());
					 nomfield.setText      (table.getModel().getValueAt(ligne,1).toString());
					 prenomfield.setText   (table.getModel().getValueAt(ligne,2).toString());
					 sectionfield.setText  (table.getModel().getValueAt(ligne,3).toString());
					 groupefield.setText   (table.getModel().getValueAt(ligne,4).toString());
		        }
		    });
			scrollPane.setViewportView(table);
			
			prepared=conx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultat));
			
			
		} catch (SQLException e) {
			
			// TODO: handle exception
			System.out.print("****************");
			e.printStackTrace();
		}
	}
}
