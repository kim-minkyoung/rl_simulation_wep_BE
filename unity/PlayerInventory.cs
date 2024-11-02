using UnityEngine;

public class PlayerInventory : MonoBehaviour
{
    public GameObject currentWeapon; // 현재 무기

    public void EquipWeapon(GameObject weapon)
    {
        if (currentWeapon != null)
        {
            // 이전 무기 비활성화 (또는 제거)
            Destroy(currentWeapon);
        }

        // 새 무기 장착
        currentWeapon = Instantiate(weapon, transform.position, Quaternion.identity);
        currentWeapon.transform.parent = transform; // 플레이어의 자식으로 설정
    }
}
