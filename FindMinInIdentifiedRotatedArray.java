package $20170512;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * 在升序旋转数组中搜索最小元素
 * 
 * 假设数组中没有重复元素
 * 
 * @author liang
 *
 */
public class FindMinInIdentifiedRotatedArray {

	public FindMinInIdentifiedRotatedArray() {
	}
	
	public void prepare(int length) {
		
		if (length <= 0) {
			return;
		}
		
		// 定义一个长度为 length 的列表
		list = new ArrayList<Integer>(length);
		
		int next;
		for (int i = 0; i < length; i++) {
			// 初始化数据并保证列表中没有重复节点且都大于0
			do {
				next = r.nextInt(2 * length);
			} while (list.contains(next) || next == 0);
			list.add(next);
		}
		
		// 将列表内容排序
		Collections.sort(list);
		
		// 将列表内容在随机位置翻转
		if (length > 1) {
			Collections.rotate(list, 1 + r.nextInt(length - 1));
		}
		
		// 输出列表中的所有节点
		System.out.print("value:	");
		for (int i = 0; i < length - 1; i++) {
			System.out.printf("%2d, ", list.get(i));
		}
		System.out.printf("%2d\n", list.get(length - 1));
		
		// 输出列表中每个节点的位置
		System.out.print("pos:	");
		for (int j = 0; j < length - 1; j++) {
			System.out.printf("%2d, ", j);
		}
		System.out.printf("%2d\n", length - 1);
	}
	
	/*
	 * 					+
	 * 				+
	 * 			+
	 * 		+
	 * 	+
	 * 								+
	 * 							+
	 * 						+
	 * 
	 * 	-	-	-	-	-	-	-	-
	 *  0	1	2	3	4	5	6	7
	 *  -	-	-	-	-	-	-	-
	 *  from						to
	 *  
	 *  mid = from + (to - from) / 2
	 *  
	 *  由于对于升序数据进行了旋转
	 *  所以升序旋转数组中必有首节点大于尾节点
	 *  即 list.get(from) > list.get(to)
	 *  
	 *  *!*!* 最大值/最小值处在旋转点附近 *!*!*
	 *  
	 *  采用二分法搜索目标时 首先取中点节点 list.get(mid)
	 *  
	 *  循环条件与二分法一致 为首节点位置不大于尾节点位置 [while (from <= to)]
	 *  
	 *  循环开始前 先检查两种特殊情况 即旋转点在 1 或 list.size() - 1 时
	 *  此时 最大值/最小值应为 list.get(0)/list.get(1) 或 list.get(list.size() - 2)/list.get(list.size() - 1)
	 *  
	 *  然后循环开始
	 *  
	 *  	若 [if] 中点节点比两个的节点值都大 [list.get(mid) > list.get(mid - 1) && list.get(mid) > list.get(mid + 1)]
	 *  	则最大节点为中点节点后一个节点 返回此位置 [mid + 1]
	 *  
	 * 		或者 若 [else if] 中点节点大于首节点 [list.get(mid) > list.get(from)]
	 *  	则左半部分数组为严格升序 旋转点不在左半部分而是在右半部分 
	 *  	则首节点应改为中点节点 [from = mid]
	 *  	此处未将 from 赋值成 mid + 1 是因为中点有可能是最大值所在的点 不能略过
	 *  
	 *  	或者 右半部分数组为严格升序 旋转点在左半部分而不是在右半部分
	 *  	则尾节点应改为中点节点 [to = mid]
	 *  	此处未将 to 赋值成 mid - 1 是因为中点有可能是最小值所在的点 不能略过
	 *  
	 * 		重新计算中点位置 [mid = from + (to - from) / 2] 否则可能陷入无限循环
	 * 
	 * 循环结束
	 *  
	 */
	
	public int find(int from, int to) {
		if (from < list.size() - 1 && list.get(from) > list.get(from + 1)) {
			// 如首节点不是最后一个节点且比后一个节点大
			// 则首节点的后一个节点是最小值
			return from + 1;
		} else if (to > 0 && list.get(to - 1) > list.get(to)) {
			// 如尾节点不是第一个节点且比前一个节点小
			// 则尾节点是最小值
			return to;
		}
		
		// 取区间中点
		int mid = from + (to - from) / 2;
		
		while (from <= to) {
			if (list.get(mid) > list.get(mid - 1) && list.get(mid) > list.get(mid + 1)) {
				// 如果中点节点比两个的节点都大 则中点节点为最大值 后一个节点即为最小值 目标已找到
				return mid + 1;
			} else if (list.get(mid) > list.get(from)) {
				// 前半部分有序
				from = mid;
			} else {
				// 目标在前半部分
				to = mid;
			}
			// 重新计算中点位置 否则可能进入无限循环状态
			mid = from + (to - from) / 2;
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		FindMinInIdentifiedRotatedArray ra = new FindMinInIdentifiedRotatedArray();
		int size = 10;
		
		// append -ea to enable assert in VM parameters
		assert size > 1 : "size must be greater than 1";
		ra.prepare(size);
			
		int found = ra.find(0, list.size() - 1);
		System.out.println("found: " + found + (found == -1 ? "" : (", value: " + list.get(found))));
	}
	
	private static List<Integer> list = null;
	
	private static Random r = new Random(System.currentTimeMillis());
}
