package $20170512;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * 在升序旋转数组中搜索指定元素
 * 
 * 假设数组中没有重复元素
 * 
 * @author liang
 *
 */
public class FindTargetInIdentifiedRotatedArray {

	public FindTargetInIdentifiedRotatedArray() {
	}
	
	public void prepare(int length) {
		// 定义一个长度为 length 的列表
		list = new ArrayList<Integer>(length);
		
		int next;
		for (int i = 0; i < length; i++) {
			// 初始化数据并保证列表中没有重复节点
			do {
				next = r.nextInt(2 * length);
			} while (list.contains(next));
			list.add(next);
		}
		
		// 将列表内容排序
		Collections.sort(list);
		
		// 将列表内容在随机位置翻转
		Collections.rotate(list, 1 + r.nextInt(length - 1));
		
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
	 *  采用二分法搜索目标时 首先取中点节点 list.get(mid)
	 *  
	 *  循环条件与二分法一致 为首节点位置不大于尾节点位置 [while (from <= to)]
	 *  
	 *  循环开始
	 *  	若 [if] 中点节点即为目标节点 [list.get(mid) == target] 则找到了目标原素 返回此位置 [mid]
	 *  
	 * 		或者 若 [else if] 中点节点大于首节点 [list.get(mid) > list.get(from)]
	 *  	则左半部分数组为严格升序 旋转点不在左半部分而是在右半部分 
	 *  		在此情况下
	 *  			若 [else if 中的 if] 目标节点在左半部分 [list.get(from) <= target < list.get(mid)]
	 *  			则尾节点应改为中点节点前一个节点 [to = mid - 1]
	 *  			此处未将 to 赋值成 mid 是因为这个分支的判断条件为 target < list.get(mid) 而非 target <= list.get(mid)
	 *  
	 *  			若 [else if 中的 else] 目标节点在右半部分
	 *  			则首节点应改为中点节点后一个节点 [from = mid + 1]
	 *  
	 *  	或者 右半部分数组为严格升序 旋转点在左半部分而不是在右半部分
	 *  		在此情况下
	 *  			若 [else if 中的 if] 目标节点在右半部分 [list.get(mid) < target <= list.get(to)]
	 *  			则首节点应改为中点节点后一个节点 [from = mid + 1]
	 *  			此处未将 from 赋值成 mid 是因为这个分支的判断条件为 list.get(mid) < target 而非 list.get(mid) <= target
	 *  
	 *  			若 [else if 中的 else] 目标节点在左半部分
	 *  			则尾节点应改为中点节点前一个节点 [to = mid - 1]
	 * 
	 * 		重新计算中点位置 [mid = from + (to - from) / 2] 否则可能陷入无限循环
	 * 
	 * 循环结束
	 *  
	 */
	
	public int find(final int target, int from, int to) {
		// 取区间中点
		int mid = from + (to - from) / 2;
		
		while (from <= to) {
			if (list.get(mid) == target) {
				// 如果中点即为目标 表明已找到
				return mid;
			} else if (list.get(mid) > list.get(from)) {
				// 前半部分有序
				if (list.get(from) <= target && target <= list.get(mid)) {
					// 目标在前半部分
					to = mid - 1;
				} else {
					// 目标在后半部分
					from = mid + 1;
				}
			} else {
				// 后半部分有序
				if (list.get(mid) <= target && target <= list.get(to)) {
					// 目标在后半部分
					from = mid + 1;
				} else {
					// 目标在前半部分
					to = mid - 1;
				}
			}
			// 重新计算中点位置 否则可能进入无限循环状态
			mid = from + (to - from) / 2;
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		FindTargetInIdentifiedRotatedArray ra = new FindTargetInIdentifiedRotatedArray();
		int size = 10;
		
		// append -ea to enable assert in VM parameters
		assert size > 1 : "size must be greater than 1";
		ra.prepare(size);
		
		int target = 1 + r.nextInt(size - 1);
		int found = ra.find(target, 0, size - 1);
		System.out.println("target: " + target + ", found: " + found + (found == -1 ? "" : (", value: " + list.get(found))));
	}
	
	private static List<Integer> list = null;
	
	private static Random r = new Random(System.currentTimeMillis());
}
