using UnityEngine;

public class WeaponPickup : MonoBehaviour
{
    public GameObject weaponPrefab; // 주울 무기 프리팹
    public float pickupRadius = 1f; // 주울 수 있는 범위

    void Update()
    {
        // 플레이어가 주울 수 있는지 체크
        if (Input.GetKeyDown(KeyCode.E)) // E 키로 주움
        {
            Collider2D[] colliders = Physics2D.OverlapCircleAll(transform.position, pickupRadius);
            foreach (Collider2D collider in colliders)
            {
                if (collider.CompareTag("Player")) // 플레이어와 충돌할 때
                {
                    // 무기 주우는 로직
                    PickupWeapon();
                    break;
                }
            }
        }
    }

    void PickupWeapon()
    {
        PlayerInventory playerInventory = FindObjectOfType<PlayerInventory>();
        if (playerInventory != null && weaponPrefab != null)
        {
            playerInventory.EquipWeapon(weaponPrefab); // 플레이어의 인벤토리에 무기 추가
        }

        Debug.Log("Weapon picked up!");
        Destroy(gameObject); // 무기 오브젝트 제거
    }

}
