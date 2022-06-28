package sample.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class FirmsDAO {
    public static void create(Firms firm) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymas";
        String querry = "INSERT INTO `firms`(`name`, `code`, `address`) VALUES (?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");

            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            preparedStatement.setString(1, firm.getName());
            preparedStatement.setString(2, firm.getCode());
            preparedStatement.setString(3, firm.getAddress());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteById(int id) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymas";
        String delete = "DELETE FROM firms WHERE id = ?";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");

            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

            System.out.println("Pavyko ištrinti įrašą");
        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println("Įrašo ištrinti nepavyko");
        }
    }

    public static List<Firms> searchByName(String name) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymas";
        String querry = "";


        if (name.isEmpty()) {
            querry = "SELECT * FROM `firms`";
        } else {
            querry = "SELECT * FROM `firms` WHERE `name` LIKE '%" + name + "%'";
        }

        ArrayList<Firms> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            //         if(!name.isEmpty()){
            //             preparedStatement.setString(1, name);
            //       }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { //Kol turime sarase elementus
                list.add(new Firms(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("code"),
                        resultSet.getString("address")
                ));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void update(Firms firms) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/atsiskaitymas";
        String update = "UPDATE `firms` SET `name` = ?, `code` = ?, `address` = ? WHERE `id` = ?";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, firms.getName());
            preparedStatement.setString(2, firms.getCode());
            preparedStatement.setString(3, firms.getAddress());
            preparedStatement.setInt(4, firms.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            System.out.println("Pavyko atnaujinti įrašą");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nepavyko atnaujinti įrašą");
        }
    }
}
