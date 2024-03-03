import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewFrame extends JFrame
{
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet result;

    JPanel upPanel1 = new JPanel();
    JPanel midPanel1 = new JPanel();
    JPanel downPanel1 = new JPanel();

    JPanel upPanel2 = new JPanel();
    JPanel midPanel2 = new JPanel();
    JPanel downPanel2 = new JPanel();

    JLabel lastNameLabel = new JLabel("Търсене на фамилия:");
    JLabel cityLabel = new JLabel("Търсене на град:");

    JLabel productNameLabel = new JLabel("Търсене на име на продукт:");
    JLabel productPriceLabel = new JLabel("Търсене на цена на продукт:");

    JTextField lastNameTextField = new JTextField();
    JTextField cityTextField = new JTextField();

    JTextField productNameTextField = new JTextField();
    JTextField productPriceTextField = new JTextField();

    JButton searchButton1 = new JButton("Търсене");

    JButton searchButton2 = new JButton("Търсене");

    JTable table1 = new JTable();
    JTable table2 = new JTable();

    JScrollPane myScroll1 = new JScrollPane(table1);
    JScrollPane myScroll2 = new JScrollPane(table2);

    public NewFrame()
    {
        this.setLayout(new GridLayout(2,1));

        upPanel1.setLayout(new GridLayout(5, 1));

        upPanel1.add(lastNameLabel);
        upPanel1.add(lastNameTextField);
        upPanel1.add(cityLabel);
        upPanel1.add(cityTextField);

        this.add(upPanel1);

        midPanel1.add(searchButton1);

        this.add(midPanel1);

        myScroll1.setPreferredSize(new Dimension(600, 125));
        downPanel1.add(myScroll1);

        this.add(downPanel1);

        upPanel2.setLayout(new GridLayout(5,1));

        upPanel2.add(productNameLabel);
        upPanel2.add(productNameTextField);
        upPanel2.add(productPriceLabel);
        upPanel2.add(productPriceTextField);

        this.add(upPanel2);

        midPanel2.add(searchButton2);

        this.add(midPanel2);

        myScroll2.setPreferredSize(new Dimension(600, 125));
        downPanel2.add(myScroll2);

        this.add(downPanel2);

        productPriceTextField.setText("0");

        searchButton1.addActionListener(new SearchAction1());

        searchButton2.addActionListener(new SearchAction2());

        this.setSize(1850, 350);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void clearForm1()
    {
        lastNameTextField.setText("");
        cityTextField.setText("");
    }

    public void clearForm2()
    {
        productNameTextField.setText("");
        productPriceTextField.setText("0");
    }

    class SearchAction1 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DBConnection.getConnection();

            String sql = "select * from customers where lastname=? or city=?";

            try
            {
                state = conn.prepareStatement(sql);

                state.setString(1, lastNameTextField.getText());
                state.setString(2, cityTextField.getText());

                state.execute();

                result = state.executeQuery();
                table1.setModel(new MyModel(result));

                clearForm1();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    class SearchAction2 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DBConnection.getConnection();

            String sql = "select * from products where productname=? or productprice=?";

            try
            {
                state = conn.prepareStatement(sql);

                state.setString(1, productNameTextField.getText());
                state.setDouble(2, Double.parseDouble(productPriceTextField.getText()));

                state.execute();

                result = state.executeQuery();
                table2.setModel(new MyModel(result));

                clearForm2();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}