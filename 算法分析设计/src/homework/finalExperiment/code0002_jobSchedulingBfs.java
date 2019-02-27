package homework.finalExperiment;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

public class code0002_jobSchedulingBfs {

    private List<Machine> machines = new ArrayList<>();
    private List<Job> jobs = new ArrayList<>();
    private int procedureSum = 0;  //所有工作的所有工序的总数
    private int maxMachinePerWorking = 0;
    private int allJobDividedWorking = 0;
    private int initTime = Integer.MAX_VALUE;
    private Queue<String> dataQueue = new LinkedList<>();  //打印局部最优解的过程
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

    //标识类，记录当前所有job中加工的工序的位置。
    class Sign{

        int jobSize;
        int jobProcedureSign[];

        public Sign(){
            this.jobSize = jobs.size();
        }

        public void setJobProcedureSign(int[] jobProcedureSign) {
            this.jobProcedureSign = jobProcedureSign;
        }
    }

    class Time{

        int jobsSize;
        int machineSize;
        int jobsCurTime[];
        int machinesCurTime[];

        public Time(){
            this.jobsSize = jobs.size();
            this.machineSize = machines.size();
        }

        public void setJobsCurTime(int[] jobsCurTime) {
            this.jobsCurTime = jobsCurTime;
        }

        public void setMachinesCurTime(int[] machinesCurTime) {
            this.machinesCurTime = machinesCurTime;
        }
    }


    public void copyArray(int origin[],int dest[]){
        for(int i = 0; i < origin.length; i++){
            dest[i] = origin[i];
        }
    }

    /**
     * 广度优先搜索实现思路：
     * 1）每次加工一个工序（一般有三种选择，除非有job已经加工完毕了）：
     * 2）第一次有三种选择，job1_1，job2_1或者job3_1,三个job都入队列
     * 3）出队列的是job1_1，它可以继续选择job1_2，job2_1或者job3_1入队列
     *  （重复的工序进入队列没有关系，需要有一个标识来记录当前入队列的状态，标识也要入队列）
     * 4）至于如何同步job和machine的时间，还得需要一个标记来记录job和machine的时间，方便同步
     *
     * 需要2个队列：一个是标记队列，另一个是job，machine的时间标记队列
     */
    public void bfs(){

        double procedureSize = procedureSum;
        double jobSize = jobs.size();
        Queue<Time> curTimeQueue = new LinkedList<>();  //每个作业加工到的时间以及每个machine加工到的时间的队列
        Queue<Sign> signQueue = new LinkedList<>();  //标记的队列

        //初始化Sign队列和Time队列:分别将所有作业的第一道工序以及标记加入队列，并同步时间，记录标记
        //第一次有三种选择，job1_1，job2_1或者job3_1,三个job都入队列
        for(int i = 0; i < jobs.size();i++){

            //工序入队列
            Time time = new Time();
            int jobsCurTime[] = new int[jobs.size()];
            int machineCurTime[] = new int[machines.size()];
            jobsCurTime[i] = jobs.get(i).jobProcedures.get(0).duration;

            int machineId = jobs.get(i).jobProcedures.get(0).machine.machineId; //获取加工工序对应的机器Id
            machineCurTime[machineId] = jobs.get(i).jobProcedures.get(0).duration;

            time.setJobsCurTime(jobsCurTime);
            time.setMachinesCurTime(machineCurTime);
            curTimeQueue.offer(time);

            //标记入队列
            int array[] = new int[jobs.size()];
            array[i] = array[i] + 1;

            Sign sign = new Sign();
            sign.setJobProcedureSign(array);
            signQueue.offer(sign);

            //字符串入队列
            String machinestr = "Machine" + machineId;
            String jobStr = "Job" + i;
            String str1 = setData(machinestr,"0", String.valueOf(jobsCurTime[i]),jobStr);
            dataQueue.offer(str1);
        }

        for(int countI = 0; countI < Math.pow(procedureSum,jobSize + 1); countI++){

            if(signQueue.isEmpty()){
                break;
            }
            else{
                Time time = curTimeQueue.poll();  //弹出时间
                Sign sign = signQueue.poll();  //弹出标记
                String str = dataQueue.poll(); //弹出字符串

                for(int i = 0; i < jobs.size(); i++){

                    int procedureId = sign.jobProcedureSign[i]; //获取当前作业加工的工序的标记

                    //如果当前作业加工到最后的工序,则加工下一个作业
                    if(procedureId == jobs.get(i).jobProcedures.size()){
                        continue;
                    }

                    int machineId = jobs.get(i).jobProcedures.get(procedureId).machine.machineId; //当前工序需要的machineId
                    int duration = jobs.get(i).jobProcedures.get(procedureId).duration; //待加工工序的时间

                    int curJobTime = time.jobsCurTime[i];
                    int curMachTime = time.machinesCurTime[machineId];

                    //记录machine和job的工作和停歇信息。
                    String machineStr = "Machine" + machineId;
                    //如果curMachTime < curJobTime,则当前M的起始时间为curJobTime
                    //如果curMachTime > curJobTime,则当前M的起始时间为curMachTime
                    String m_start = String.valueOf(curMachTime);
                    int m_end;  //m_end = m_start + duration
                    String jobId = "job" + i;

                    //如果machineCurTime < jobCurTime, machineCurTime = jobCurTime + duration
                    if(curMachTime < curJobTime){
                        m_start = String.valueOf(curJobTime);
                        curMachTime = curJobTime + duration;
                        curJobTime = curJobTime + duration;
                    }

                    //如果machineCurTime >= jobCurTime, jobCurTime = machineCurTime + duration
                    else{
                        curJobTime = curMachTime + duration;
                        curMachTime = curMachTime + duration;
                    }

                    //将当前运行的机器,运行的作业,机器的起始,结束时间添加到data中
                    m_end = Integer.parseInt(m_start) + duration;
                    String strTemp = setData(machineStr, m_start, String.valueOf(m_end), jobId);


                    //该工序加工完毕，将新的时间添加到队列中
                    Time timeChild = new Time();

                    int jobsTime[] = new int[jobs.size()];
                    int machineTime[] = new int[machines.size()];

                    copyArray(time.jobsCurTime,jobsTime);
                    copyArray(time.machinesCurTime,machineTime);

                    jobsTime[i] = curJobTime;
                    machineTime[machineId] = curMachTime;

                    timeChild.setJobsCurTime(jobsTime);
                    timeChild.setMachinesCurTime(machineTime);

                    curTimeQueue.offer(timeChild);

                    //该工序加工完毕，将新的标记添加到队列中
                    Sign signChild = new Sign();
                    int signTemp[] = new int[jobs.size()];

                    copyArray(sign.jobProcedureSign,signTemp);
                    signTemp[i] = procedureId + 1;

                    signChild.setJobProcedureSign(signTemp);

                    signQueue.offer(signChild);

                    //添加字符串序列到队列中
                    String combinedStr = str + "\r\n" + strTemp;
                    dataQueue.offer(combinedStr);

//                    //打印作业中已加工工序的情况
//                    int i1 = signChild.jobProcedureSign[0];
//                    int i2 = signChild.jobProcedureSign[1];
//                    int i3 = signChild.jobProcedureSign[2];
//
//                    //打印机器的时间情况
//                    int m1 = timeChild.machinesCurTime[0];
//                    int m2 = timeChild.machinesCurTime[1];
//                    int m3 = timeChild.machinesCurTime[2];


                    //如果所有工序加工完，则打印所有机器加工的总时间
                    if(isFinish(signChild)){

                        if(getMaxCurMachTime(timeChild) < initTime){
                            initTime = getMaxCurMachTime(timeChild);
                            System.out.println(combinedStr);
                            System.out.println("-----------------------------");
                            outputFile(combinedStr);
                        }
                    }
                }
            }
        }
    }

    //判断所有的工序是否都加工完
    public boolean isFinish(Sign sign){

        for(int i = 0; i < jobs.size(); i++){
            if(sign.jobProcedureSign[i] != jobs.get(i).jobProcedures.size()){
                return false;
            }
        }
        return true;
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
    public int getMaxCurMachTime(Time time){

        int maxMachineEnd = Integer.MIN_VALUE;

        for(int i = 0; i < machines.size(); i++){

            if(maxMachineEnd < time.machinesCurTime[i]){
                maxMachineEnd = time.machinesCurTime[i];
            }
        }

        return maxMachineEnd;
    }


    //打印machine和job工作时间的关系
    public String setData(String machine, String start, String end, String job){

        String str = machine + "," + start + "," + end + "," + job;
        return str;
    }


    //每次深搜后，将数据输出到文件中
    public void outputFile(String str){
        File file = new File("D:/编程练习/编译原理/python编译原理/词法分析/test/data" + (++num) + ".txt");
        try {
            FileOutputStream out = new FileOutputStream(file);

            out.write(str.getBytes());
            out.flush();

            System.out.println("成功将info输出到data" + num + "文件");
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void main(){

        createJobsAndMachines();

        initValue();

        System.out.println("initTime所在的区间为: [" + maxMachinePerWorking + "," + allJobDividedWorking + "]");
        System.out.println("工序的总数为:" + procedureSum);

        bfs();

        System.out.println("所有作业工序加工完后的最短时间为:" + initTime);
    }

}
