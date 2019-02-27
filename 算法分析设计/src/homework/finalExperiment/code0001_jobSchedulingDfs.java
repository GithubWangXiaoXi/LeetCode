package homework.finalExperiment;

import java.io.*;
import java.util.*;

import org.junit.Test;

public class code0001_jobSchedulingDfs {

    /**
     * 问题描述：作业车间调度：每个作业有多道工序，
     * 每道工序需要经过不同的机器加工，求所有的工作（所有工序）都完成加工后，花费最少的时间
     *
     * 实现策略(不可行)：
     * 贪心策略：如果机器空闲，则一旦有工件需要在该机器上加工，则开启接收状态:
     * 1）申请机器的工件只有一个，则直接加入到机器中
     * 2）申请机器的工件有多个，则选取加工时间最长的，加入到机器中。
     * 3）但是这样的贪心还是有反例，针对这道题，则对从起始时间开始，花费最长时间的机器所加工的工序进行全排列（找到最优解）
     *
     * 实现策略：
     * dfs深度优先搜索
     * 1,最短运行时间在[maxMachinePerWorking,allJobDividedWorking]区间内，左端点为某机器连续运行的总时间，右端点为所有工作运行完的总时间
     * 2,每个作业循环遍历，对所有符合要求（机器未被占用 / 该工序的之前工序必须加工完）的工序进行全排列（剪枝）
     * 3,每个机器记录运行到最后的时间end，可行解为所有job的所有工序都加工过的machine的maxEnd
     * 4,用一个布尔数组（len = procedureSum）记录这些工序是否全部加工
     *
     * 实现策略1：
     * 1，如何计算运行时间：之前是使用start，end来表示procedure的当前使用时间，但是这么做太精了，难以控制
     *    其实撇开machine(3个）和job（3个）,固定一个（只看一个job），变化另一个（看3个machine），
     *    当前的job的runtime和machine的runtime其实有两种情况：
     *    1）job中有些工序在其他machine加工，可能导致job的runtime比当前machine的runtime大。
     *    2）machine加工了其它的job，可能导致machine的runtime比当前的job的runtime大。
     *    如果是1)的情况，则machinecurTimeQueue < jobcurTimeQueue,则需要同步时间，才能将工序放入machine中去加工，machinecurTimeQueue = jobcurTimeQueue + duration
     *    如果是2)的情况，则machinecurTimeQueue > jobcurTimeQueue,则需要同步时间, jobcurTimeQueue = machinecurTimeQueue + duration
     *    这样只需保证在添加时考虑这两种状态，就能同步machine和job的时间
     *
     *    不要迷惑在如何保证多个machine之间时间同步的问题，虽然说多个machine中的最长运行时间即为可行解，但是还是要转换下思维
     * job才是主角，machine是通过job中的工序唤醒的，是配角，不要考虑配角之间时间的同步问题。以及如何实现不同machine之间如何并发加工的问题（不用并发）
     *
     * 2，如何dfs深度优先搜索呢？之前dfs全排列时，用一个标记数组来标记该位置（盒子）是否有数字（扑克牌），
     * 然后通过回溯，将最后放入的牌取出，回到上一个状态（例如取出3，回到2状态），上一个状态（2）需要重新遍历那个位置是空的，并放入
     *    而工作车间调度问题如何能保证所有工作，所有工序全排列一遍呢。二维的东西如何做到呢？由于每个job的工序只能往后走，这里使用curProcedureId来表示job当前走的工序位置
     * 对所有的job进行遍历（深度固定，为总工序数，而curProcedureId是横向移动，而不同job之间是纵向移动，这样在改变深度搜索时，必能保证每个状态搜索的工序是不一样的，实现所有工序全排列）
     */


    private List<Machine> machines = new ArrayList<>();
    private List<Job> jobs = new ArrayList<>();
    private int procedureSum = 0;  //所有工作的所有工序的总数
    private int maxMachinePerWorking = 0;
    private int allJobDividedWorking = 0;
    private int initTime = Integer.MAX_VALUE;
    private List<String> data = new ArrayList<>();
    int num = 0;


    /**
     * Job的属性：
     * 1，每个作业有编号。
     * 2，每个作业有Procedure序列。
     * 3，以及job当前加工工序是哪一个
     * 4，job的当前工作时间
     */
    class Job{

        Integer jobId;
        List<Procedure> jobProcedures;
        Integer curProcedureId;
        Integer curJobTime;

        public Job(Integer code) {
            this.jobId = code;
            jobProcedures = new ArrayList<>();
            curProcedureId = 0;
            curJobTime = 0;
        }

        public void addProcedures(Procedure Procedure) {
            jobProcedures.add(Procedure);
        }
    }

    /**
     * Procedure属性:(属性过多，不便于解决问题)
     * 1,在哪台机器上加工,
     * 2,加工时间多少，
     //     * 3,该工序起始加工时间（machine想要加入一道工序，则machine的结束时间就是工序的start）
     //     * 4,该工序的结束时间（machine运行完一道工序后，则machine的结束时间就是工序的end）
     */
    class Procedure{

        Machine machine;
        Integer duration;

        public Procedure(Machine machine, Integer duration) {
            this.machine = machine;
            this.duration = duration;
        }

        public String toString() {
            return "machine: " + this.machine.machineId + ", duration: " + this.duration;
        }
    }

    /**
     * machine的属性：(至于machine的free属性，根本没必要。同一个代码块中，一行代码，就表示工序完成加工，free从false变成true，在递归时实现不到lock的功能)
     * 1,机器有对应的编号
     * 2,机器的最后结束时间
     */
    class Machine{

        int machineId;
        Integer curMachTime;

        public Machine(int machineId) {
            this.machineId = machineId;
            this.curMachTime = 0;
        }

    }


    //深度优先搜索，深度已知，为总工序数，列举所有情况(每次递归，加工剩余工序中的一个)
    public void dfs(int step){

        if(step == procedureSum){
            if(getMaxCurMachTime() < initTime){
                initTime = getMaxCurMachTime();
                showJobMachInfo();
                showData();
                outputFile();
            }
            return;
        }

        for(int i = 0; i < jobs.size(); i++){ //遍历每个job

            int curProcedureId = jobs.get(i).curProcedureId;  //当前job执行到的工序的位置
            int curJobTime = jobs.get(i).curJobTime;  //当前job运行到的时间

            if(curProcedureId == jobs.get(i).jobProcedures.size()){  //如果当前的job已经加工完毕，则到另一个job中寻找是否有没加工的procedure
                continue;
            }

            int machineId = jobs.get(i).jobProcedures.get(curProcedureId).machine.machineId;
            int curMachTime = machines.get(machineId).curMachTime;
            int duration = jobs.get(i).jobProcedures.get(curProcedureId).duration;

            //记录machine和job的工作和停歇信息。
            String machineStr = "Machine" + machineId;
            //如果curMachTime < curJobTime,则当前M的起始时间为curJobTime
            //如果curMachTime > curJobTime,则当前M的起始时间为curMachTime
            String m_start = String.valueOf(curMachTime);
            int m_end;  //m_end = m_start + duration
            String jobId = "job" + i;

            //如果machinecurTimeQueue < jobcurTimeQueue, machinecurTimeQueue = jobcurTimeQueue + duration
            if(curMachTime < curJobTime){
                m_start = String.valueOf(curJobTime);
                machines.get(machineId).curMachTime = curJobTime + duration;
                jobs.get(i).curJobTime = curJobTime + duration;
            }

            //如果machinecurTimeQueue > jobcurTimeQueue, jobcurTimeQueue = machinecurTimeQueue + duration
            else{
                jobs.get(i).curJobTime = curMachTime + duration;
                machines.get(machineId).curMachTime = curMachTime + duration;
            }

            //该job的当前工序执行完，当前的job的curProcedureId++
            jobs.get(i).curProcedureId++;

            //将当前运行的机器,运行的作业,机器的起始,结束时间添加到data中
            m_end = Integer.parseInt(m_start) + duration;
            setData(machineStr, m_start, String.valueOf(m_end), jobId);

            dfs(step + 1);

            //深搜一遍后，需要将之前在jobs，machine中修改的值改回来,并且还要将该消息在data中删除
            deleteData();
            jobs.get(i).curJobTime = curJobTime;
            machines.get(machineId).curMachTime = curMachTime;
            jobs.get(i).curProcedureId = curProcedureId;
        }
    }


    //加载机器和作业
    public void createJobsAndMachines(){

        //创建3台机器
        Machine machine0 = new Machine(0);
        Machine machine1 = new Machine(1);
        Machine machine2 = new Machine(2);

        //创建3个job
        Job job_A = new Job(0);
        Procedure ProcedureA_1 = new Procedure(machine0,3);
        Procedure ProcedureA_2 = new Procedure(machine1,2);
        Procedure ProcedureA_3 = new Procedure(machine2,2);
        job_A.addProcedures(ProcedureA_1);
        job_A.addProcedures(ProcedureA_2);
        job_A.addProcedures(ProcedureA_3);

        Job job_B = new Job(1);
        Procedure ProcedureB_1 = new Procedure(machine0,2);
        Procedure ProcedureB_2 = new Procedure(machine2,1);
        Procedure ProcedureB_3 = new Procedure(machine1,4);
        job_B.addProcedures(ProcedureB_1);
        job_B.addProcedures(ProcedureB_2);
        job_B.addProcedures(ProcedureB_3);


        Job job_C = new Job(2);
        Procedure ProcedureC_1 = new Procedure(machine1,4);
        Procedure ProcedureC_2 = new Procedure(machine2,3);
        job_C.addProcedures(ProcedureC_1);
        job_C.addProcedures(ProcedureC_2);

        jobs.add(job_A);
        jobs.add(job_B);
        jobs.add(job_C);

        machines.add(machine0);
        machines.add(machine1);
        machines.add(machine2);
    }


    //初始化MaxMachinePerWorking和AllJobDividedWorking,以及工序的总数
    public void initValue(){

        int max[] = new int[machines.size()];

        //遍历每台机器
        for (int i = 0; i < machines.size(); i++){

            //遍历每个job
            for(int j = 0; j < jobs.size();j++){

                //遍历每个job的所有工序
                for(int k = 0; k < jobs.get(j).jobProcedures.size(); k++){

                    if(i == jobs.get(j).jobProcedures.get(k).machine.machineId){
                        max[i] = max[i] + jobs.get(j).jobProcedures.get(k).duration;
                    }

                    allJobDividedWorking += jobs.get(j).jobProcedures.get(k).duration;
                }

                procedureSum += jobs.get(j).jobProcedures.size();
            }
        }

        Arrays.sort(max);

        allJobDividedWorking = allJobDividedWorking / machines.size();

        maxMachinePerWorking = max[machines.size() - 1];

        procedureSum = procedureSum / machines.size();
    }

    //得到maxMachineEnd
    public int getMaxCurMachTime(){

        int maxMachineEnd = Integer.MIN_VALUE;

        for(int i = 0; i < machines.size(); i++){

            if(maxMachineEnd < machines.get(i).curMachTime){
                maxMachineEnd = machines.get(i).curMachTime;
            }
        }

        return maxMachineEnd;
    }

    //打印job,machine的curTimeQueue信息
    public void showJobMachInfo(){

        System.out.println("machine Info:");
        for(int i = 0; i < machines.size(); i++){
            System.out.println("machine" + i + " : " +machines.get(i).curMachTime);
        }

        System.out.println("jobs Info:");
        for(int j = 0; j < jobs.size(); j++){

//        	for(Procedure procedure : jobs.get(j).jobProcedures) {
//        		 System.out.println(procedure);
//        	}

//            System.out.println("job" + j + " : " + jobs.get(j).curJobTime + " 当前job位置: " + jobs.get(j).curProcedureId);
            System.out.println("job" + j + " : " + jobs.get(j).curJobTime);
//            System.out.println();
        }

        System.out.println("---------------------------------------------------");
    }

    //打印machine和job工作时间的关系
    public void setData(String machine, String start, String end, String job){

        if(data.size() != procedureSum){
            String str = machine + "," + start + "," + end + "," + job;
            data.add(str);
        }
    }

    //dfs在回溯时，需要将list中的数据删除，删除最后一条数据
    public void deleteData(){

         if(data.isEmpty()){
             return;
         }
         int index = data.size() - 1;
         data.remove(index);
    }

    public void showData(){

        for(int i = 0; i < data.size(); i++){
            System.out.println(data.get(i));
        }
        System.out.println("************************");
    }


    //每次深搜后，将数据输出到文件中
    public void outputFile(){
        File file = new File("D:/编程练习/编译原理/python编译原理/词法分析/test/data" + (++num) + ".txt");
        try {
            Writer out = new FileWriter(file);
            for(String str : data){
                out.write(str + "\r\n");
                out.flush();
            }
            System.out.println("成功将info输出到data" + num + "文件");
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void main(){

        createJobsAndMachines();

        //用矩阵的方式来记录工序是否全部加工完毕,其实不用这么考虑，只需要判断curProcedureId是否等于job.procedureList.size()即可
//        boolean sign[][] = new boolean[jobs.size()][];

        initValue(); //剪枝1(总时间在[maxMachinePerWorking,allJobDividedWorking]内)

        System.out.println("initTime所在的区间为: [" + maxMachinePerWorking + "," + allJobDividedWorking + "]");
        System.out.println("工序的总数为:" + procedureSum);

        dfs(0);

        System.out.println("所有作业工序加工完后的最短时间为:" + initTime);
    }

}
