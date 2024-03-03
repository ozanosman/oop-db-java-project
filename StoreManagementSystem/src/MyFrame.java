import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyFrame extends JFrame
{
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet result;

    int id1 = -1;
    int id2 = -1;
    int id3 = -1;

    JPanel upPanel1 = new JPanel();
    JPanel midPanel1 = new JPanel();
    JPanel downPanel1 = new JPanel();

    JPanel upPanel2 = new JPanel();
    JPanel midPanel2 = new JPanel();
    JPanel downPanel2 = new JPanel();

    JPanel upPanel3 = new JPanel();
    JPanel midPanel3 = new JPanel();
    JPanel downPanel3 = new JPanel();

    JLabel firstNameLabel = new JLabel("Име:");
    JLabel lastNameLabel = new JLabel("Фамилия:");
    JLabel sexLabel = new JLabel("Пол:");
    JLabel phoneLabel = new JLabel("Телефон:");
    JLabel cityLabel = new JLabel("Град:");

    JLabel productNameLabel = new JLabel("Име на продукт:");
    JLabel productPriceLabel = new JLabel("Цена на продукт:");

    JLabel customerIdLabel = new JLabel("Идентификационен номер на клиент:");
    JLabel productIdLabel = new JLabel("Идентификационен номер на продукт:");

    JTextField firstNameTextField = new JTextField();
    JTextField lastNameTextField = new JTextField();
    JTextField phoneTextField = new JTextField();
    JTextField cityTextField = new JTextField();

    JTextField productNameTextField = new JTextField();
    JTextField productPriceTextField = new JTextField();

    JTextField customerIdTextField = new JTextField();
    JTextField productIdTextField = new JTextField();

    String[] sexCollection = {"Мъж", "Жена"};
    JComboBox<String> sexCombo = new JComboBox<String>(sexCollection);

    JButton addButton1 = new JButton("Добавяне");
    JButton deleteButton1 = new JButton("Изтриване");
    JButton editButton1 = new JButton("Редактиране");

    JButton addButton2 = new JButton("Добавяне");
    JButton deleteButton2 = new JButton("Изтриване");
    JButton editButton2 = new JButton("Редактиране");

    JButton addButton3 = new JButton("Добавяне");
    JButton deleteButton3 = new JButton("Изтриване");
    JButton editButton3 = new JButton("Редактиране");

    JComboBox<String> refreshCombo = new JComboBox<String>();

    JTable table1 = new JTable();
    JTable table2 = new JTable();
    JTable table3 = new JTable();

    JScrollPane myScroll1 = new JScrollPane(table1);
    JScrollPane myScroll2 = new JScrollPane(table2);
    JScrollPane myScroll3 = new JScrollPane(table3);

    public MyFrame()
    {
        this.setLayout(new GridLayout(3,1));

        upPanel1.setLayout(new GridLayout(5, 1));

        upPanel1.add(firstNameLabel);
        upPanel1.add(firstNameTextField);
        upPanel1.add(lastNameLabel);
        upPanel1.add(lastNameTextField);
        upPanel1.add(sexLabel);
        upPanel1.add(sexCombo);
        upPanel1.add(phoneLabel);
        upPanel1.add(phoneTextField);
        upPanel1.add(cityLabel);
        upPanel1.add(cityTextField);

        this.add(upPanel1);

        midPanel1.add(addButton1);
        midPanel1.add(deleteButton1);
        midPanel1.add(editButton1);

        midPanel1.add(refreshCombo);

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

        midPanel2.add(addButton2);
        midPanel2.add(deleteButton2);
        midPanel2.add(editButton2);

        this.add(midPanel2);

        myScroll2.setPreferredSize(new Dimension(600, 125));
        downPanel2.add(myScroll2);

        this.add(downPanel2);

        upPanel3.setLayout(new GridLayout(5,1));

        upPanel3.add(customerIdLabel);
        upPanel3.add(customerIdTextField);
        upPanel3.add(productIdLabel);
        upPanel3.add(productIdTextField);

        this.add(upPanel3);

        midPanel3.add(addButton3);
        midPanel3.add(deleteButton3);
        midPanel3.add(editButton3);

        this.add(midPanel3);

        myScroll3.setPreferredSize(new Dimension(600, 125));
        downPanel3.add(myScroll3);

        this.add(downPanel3);

        refreshTable1();
        refreshSexCombo();
        refreshTable2();
        refreshTable3();

        table1.addMouseListener(new MouseAction1());
        addButton1.addActionListener(new AddAction1());
        deleteButton1.addActionListener(new DeleteAction1());

        table2.addMouseListener(new MouseAction2());
        addButton2.addActionListener(new AddAction2());
        deleteButton2.addActionListener(new DeleteAction2());

        table3.addMouseListener(new MouseAction3());
        addButton3.addActionListener(new AddAction3());
        deleteButton3.addActionListener(new DeleteAction3());

        this.setSize(1850, 450);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void refreshTable1()
    {
        conn = DBConnection.getConnection();

        try
        {
            state = conn.prepareStatement("select * from customers");
            result = state.executeQuery();
            table1.setModel(new MyModel(result));
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

    public void refreshTable2()
    {
        conn = DBConnection.getConnection();

        try
        {
            state = conn.prepareStatement("select * from products");
            result = state.executeQuery();
            table2.setModel(new MyModel(result));
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

    public void refreshTable3()
    {
        conn = DBConnection.getConnection();

        try
        {
            state = conn.prepareStatement("select * from orders");
            result = state.executeQuery();
            table3.setModel(new MyModel(result));
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

    public void refreshSexCombo()
    {
        refreshCombo.removeAllItems();

        String item = "";
        String sql = "select id, firstname, lastname from customers";

        conn = DBConnection.getConnection();

        try
        {
            state = conn.prepareStatement(sql);
            result = state.executeQuery();

            while (result.next())
            {
                item = result.getObject(1).toString() + "." + result.getObject(2).toString() + " " + result.getObject(3);
                refreshCombo.addItem(item);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    class MouseAction1 implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int row = table1.getSelectedRow();

            id1 = Integer.parseInt(table1.getValueAt(row, 0).toString());

            if (e.getClickCount() > 1)
            {
                firstNameTextField.setText(table1.getValueAt(row, 1).toString());
                lastNameTextField.setText(table1.getValueAt(row, 2).toString());
                phoneTextField.setText(table1.getValueAt(row, 4).toString());
                cityTextField.setText(table1.getValueAt(row, 5).toString());

                if (table1.getValueAt(row, 3).toString().equals("Мъж"))
                {
                    sexCombo.setSelectedIndex(0);
                }
                else
                {
                    sexCombo.setSelectedIndex(1);
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {

        }

        @Override
        public void mousePressed(MouseEvent e)
        {

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {

        }
    }

    class MouseAction2 implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int row = table2.getSelectedRow();

            id2 = Integer.parseInt(table2.getValueAt(row, 0).toString());

            if (e.getClickCount() > 1)
            {
                productNameTextField.setText(table2.getValueAt(row, 1).toString());
                productPriceTextField.setText(table2.getValueAt(row, 2).toString());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {

        }

        @Override
        public void mousePressed(MouseEvent e)
        {

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {

        }
    }

    class MouseAction3 implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int row = table3.getSelectedRow();

            id3 = Integer.parseInt(table3.getValueAt(row, 0).toString());

            if (e.getClickCount() > 1)
            {
                customerIdTextField.setText(table3.getValueAt(row, 1).toString());
                productIdTextField.setText(table3.getValueAt(row, 2).toString());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {

        }

        @Override
        public void mousePressed(MouseEvent e)
        {

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {

        }
    }

    public void clearForm1()
    {
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        phoneTextField.setText("");
        cityTextField.setText("");
    }

    public void clearForm2()
    {
        productNameTextField.setText("");
        productPriceTextField.setText("");
    }

    public void clearForm3()
    {
        customerIdTextField.setText("");
        productIdTextField.setText("");
    }

    class AddAction1 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DBConnection.getConnection();

            String sql = "insert into customers values(null, ?, ?, ?, ?, ?)";

            try
            {
                state = conn.prepareStatement(sql);

                state.setString(1, firstNameTextField.getText());
                state.setString(2, lastNameTextField.getText());
                state.setString(3, sexCombo.getSelectedItem().toString());
                state.setInt(4, Integer.parseInt(phoneTextField.getText()));
                state.setString(5, cityTextField.getText());

                state.execute();

                refreshTable1();
                refreshSexCombo();
                clearForm1();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class AddAction2 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DBConnection.getConnection();

            String sql = "insert into products values(null, ?, ?)";

            try
            {
                state = conn.prepareStatement(sql);

                state.setString(1, productNameTextField.getText());
                state.setDouble(2, Double.parseDouble(productPriceTextField.getText()));

                state.execute();

                refreshTable2();
                clearForm2();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class AddAction3 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DBConnection.getConnection();

            String sql = "insert into orders values(null, ?, ?, current_timestamp)";

            try
            {
                state = conn.prepareStatement(sql);

                state.setInt(1, Integer.parseInt(customerIdTextField.getText()));
                state.setInt(2, Integer.parseInt(productIdTextField.getText()));

                state.execute();

                refreshTable3();
                clearForm3();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class DeleteAction1 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DBConnection.getConnection();

            String sql = "delete from customers where id=?";

            try
            {
                state = conn.prepareStatement(sql);
                state.setInt(1, id1);
                state.execute();

                refreshTable1();
                refreshSexCombo();
                clearForm1();

                id1 = -1;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class DeleteAction2 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DBConnection.getConnection();

            String sql = "delete from products where id=?";

            try
            {
                state = conn.prepareStatement(sql);
                state.setInt(1, id2);
                state.execute();

                refreshTable2();
                clearForm2();

                id2 = -1;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    class DeleteAction3 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            conn = DBConnection.getConnection();

            String sql = "delete from orders where id=?";

            try
            {
                state = conn.prepareStatement(sql);
                state.setInt(1, id3);
                state.execute();

                refreshTable3();
                clearForm3();

                id3 = -1;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}