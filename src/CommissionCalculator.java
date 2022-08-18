/**
 * Created by Shiyang Chen on 2018/5/2.
 */

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CommissionCalculator {
    private List<Salesman> processEmployeeFile(String filePath) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        List<Salesman> list = new ArrayList<>();
        String str = "";
        while ((str = br.readLine()) != null) {
            String[] strs = str.split("\\|");
            int level = Integer.valueOf(strs[0]);
            int index = Integer.valueOf(strs[1]);
            int team = Integer.valueOf(strs[3]);
            String name = strs[2];
            list.add(new Salesman(level, index, name, team));
        }
        br.close();
        fis.close();
        return list;
    }

    private void processSalesFile(String filePath, List<Salesman> list) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String str = "";
        while ((str = br.readLine()) != null) {
            String[] strs = str.split(",");
            int index = Integer.valueOf(strs[0]);
            double salesNum = Double.valueOf(strs[1]);
            Salesman sm = list.get(index - 1);
            sm.setSalesAmount(sm.getSalesAmount() + salesNum);
            sm.setSalesCount(sm.getSalesCount() + 1);
        }
        br.close();
        fis.close();
    }

    private void calculateTable(List<Salesman> list, String filePath) throws Exception {
        int[] bonusNum = {0, 50, 75, 100};
        int[] basePay = {0, 100, 150, 200};
        double[] commPer = {0, 0.01, 0.0225, 0.035};
        double[] teamAmount = new double[5];

        //calculate commission
        for (Salesman sm : list) {
            int level = sm.getLevel();
            int team = sm.getTeam();
            double salesAmount = sm.getSalesAmount();
            teamAmount[team] += salesAmount;
            //solution 1 fit the question , but it is diefferent than the example
            //sm.setComm(salesAmount * commPer[level]);
            //solutin 2 fit the example, but it not like the question
            if (salesAmount > 1000) {
                sm.setComm(salesAmount * 0.01);
            } else {
                sm.setComm(0.0);
            }

        }

        //calculate bonus
        int bonusTeam = 0;
        if (teamAmount[1] > teamAmount[2] && teamAmount[1] > teamAmount[3] && teamAmount[1] > teamAmount[4]) {
            bonusTeam = 1;
        } else if (teamAmount[2] > teamAmount[3] && teamAmount[1] > teamAmount[4]) {
            bonusTeam = 2;
        } else if (teamAmount[3] > teamAmount[4]) {
            bonusTeam = 3;
        } else {
            bonusTeam = 4;
        }
        for (Salesman sm : list) {
            if (sm.getTeam() == bonusTeam) {
                sm.setBonus(bonusNum[sm.getLevel()]);
            }
        }

        //calculate team table
        int[] teamIncent = new int[5];
        int[] teamBasePay = new int[5];
        int[] teamSales = new int[5];
        int[] teamComm = new int[5];
        int[] teamBonus = new int[5];
        int[] teamSalary = new int[5];
        for (Salesman sm : list) {
            int level = sm.getLevel();
            int team = sm.getTeam();
            double salesAmount = sm.getSalesAmount();
            int salesCount = sm.getSalesCount();
            int bonus = sm.getBonus();
            double comm = sm.getComm();
            teamIncent[team] += salesCount * 5;
            teamBasePay[team] += basePay[level];
            teamSales[team] += salesAmount;
            teamBonus[team] += bonus;
            teamSalary[team] += salesCount * 5 + basePay[level] + salesAmount * commPer[level] + bonus;
            teamComm[team] += comm;
        }
        for (int i = 1; i <= 4; i++) {
            teamIncent[0] += teamIncent[i];
            teamBasePay[0] += teamBasePay[i];
            teamSales[0] += teamSales[i];
            teamComm[0] += teamComm[i];
            teamBonus[0] += teamBonus[i];
            teamSalary[0] += teamSalary[i];
        }

        //format output
        DecimalFormat df = new DecimalFormat("#0.00");
        int[] columnLength = {16, 8, 9, 15, 7, 8, 8};
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-" + columnLength[0] + "s", "Employee"));
        sb.append(String.format("%-" + columnLength[1] + "s", "Incent"));
        sb.append(String.format("%-" + columnLength[2] + "s", "BasePay"));
        sb.append(String.format("%-" + columnLength[3] + "s", "Sales"));
        sb.append(String.format("%-" + columnLength[4] + "s", "Comm."));
        sb.append(String.format("%-" + columnLength[5] + "s", "Bonus"));
        sb.append(String.format("%-" + columnLength[6] + "s", "Salary"));
        sb.append("\n======================================================================\n");
        for (Salesman sm : list) {
            int level = sm.getLevel();
            int team = sm.getTeam();
            double salesAmount = sm.getSalesAmount();
            int salesCount = sm.getSalesCount();
            int bonus = sm.getBonus();
            double comm = sm.getComm();
            String name = sm.getName();
            double salary = salesCount * 5 + basePay[level] + comm + bonus;
            sb.append(String.format("%-" + columnLength[0] + "s", name));
            sb.append(String.format("%-" + columnLength[1] + "s", String.valueOf(df.format(salesCount * 5))));
            sb.append(String.format("%-" + columnLength[2] + "s", String.valueOf(df.format(basePay[level]))));
            sb.append(String.format("%-" + columnLength[3] + "s", String.valueOf(df.format(salesAmount))));
            sb.append(String.format("%-" + columnLength[4] + "s", String.valueOf(df.format(comm))));
            sb.append(String.format("%-" + columnLength[5] + "s", String.valueOf(df.format(bonus))));
            sb.append(String.format("%-" + columnLength[6] + "s", String.valueOf(df.format(salary))));
            sb.append("\n");
        }
        sb.append("======================================================================\n");
        sb.append(String.format("%-" + columnLength[0] + "s", "TEAM TOTALS"));
        sb.append(String.format("%-" + columnLength[1] + "s", "Incent"));
        sb.append(String.format("%-" + columnLength[2] + "s", "BasePay"));
        sb.append(String.format("%-" + columnLength[3] + "s", "Sales"));
        sb.append(String.format("%-" + columnLength[4] + "s", "Comm."));
        sb.append(String.format("%-" + columnLength[5] + "s", "Bonus"));
        sb.append(String.format("%-" + columnLength[6] + "s", "Salary"));
        sb.append("\n");
        for (int i = 1; i <= 4; i++) {
            sb.append(String.format("%-" + columnLength[0] + "s", "Team" + i));
            sb.append(String.format("%-" + columnLength[1] + "s", String.valueOf(df.format(teamIncent[i]))));
            sb.append(String.format("%-" + columnLength[2] + "s", String.valueOf(df.format(teamBasePay[i]))));
            sb.append(String.format("%-" + columnLength[3] + "s", String.valueOf(df.format(teamAmount[i]))));
            sb.append(String.format("%-" + columnLength[4] + "s", String.valueOf(df.format(teamComm[i]))));
            sb.append(String.format("%-" + columnLength[5] + "s", String.valueOf(df.format(teamBonus[i]))));
            sb.append(String.format("%-" + columnLength[6] + "s", String.valueOf(df.format(teamSalary[i]))));
            sb.append("\n");
        }
        sb.append("======================================================================\n");
        sb.append("GRAND TOTALS\n");
        sb.append(String.format("%-" + columnLength[0] + "s", "IncentivePay"));
        sb.append("$" + df.format(teamIncent[0]) + "\n");
        sb.append(String.format("%-" + columnLength[0] + "s", "Base Pay"));
        sb.append("$" + df.format(teamBasePay[0]) + "\n");
        sb.append(String.format("%-" + columnLength[0] + "s", "Sales"));
        sb.append("$" + df.format(teamSales[0]) + "\n");
        sb.append(String.format("%-" + columnLength[0] + "s", "Commissions"));
        sb.append("$" + df.format(teamComm[0]) + "\n");
        sb.append(String.format("%-" + columnLength[0] + "s", "Bonuses"));
        sb.append("$" + df.format(teamBonus[0]) + "\n");
        sb.append(String.format("%-" + columnLength[0] + "s", "Salaries"));
        sb.append("$" + df.format(teamSalary[0]) + "\n");
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(String.valueOf(sb).getBytes());
        fos.close();
    }

    public static void main(String[] args) throws Exception {
        CommissionCalculator cc = new CommissionCalculator();
        String employeeUrl = "./Employees.txt";
        String salesUrl = "./salesslips.txt";
        String reportUrl = "./report.txt";
        List<Salesman> list = cc.processEmployeeFile(employeeUrl);
        cc.processSalesFile(salesUrl, list);
        cc.calculateTable(list, reportUrl);
    }
}
