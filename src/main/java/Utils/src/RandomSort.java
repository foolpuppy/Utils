package Utils.src;

import java.util.Arrays;

public class RandomSort{
//随机互换数组两个值的位置 只到排序成功
	public static void main(String[] args) {
		int[] arr = { 9,5,2,1,4,6,3,0,7,8 };
		int temp, idx, xdi, cnt = 0;
		while (true) {
			// 统计次数
			System.out.println(++cnt + "次");
			idx = (int) (Math.random() * arr.length);
			xdi = (int) (Math.random() * arr.length);
			// 随机下标相同则互换值
			if (idx != xdi) {
				temp = arr[idx];
				arr[idx] = arr[xdi];
				arr[xdi] = temp;
				// 判断是否排序完成
				if (isSorted(arr))
					break;
			}

		}
		System.out.println("排序完成:\n" + Arrays.toString(arr));

	}

	/**
	 * @param int[] a
	 * @return boolean
	 * @see 判断数组是否按照从小到大排序
	 */
	private static boolean isSorted(int[] a) {
		boolean sorted = true;

		for (int i = 0; i < a.length - 1; ++i) {
			if (a[i] > a[i + 1]) {
				sorted = false;
			}
		}
		return sorted;

	}

}
