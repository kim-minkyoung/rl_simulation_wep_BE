using UnityEngine;

public class Projectile : MonoBehaviour
{
    public float lifespan = 2f; // 투사체 생명 시간
    public int damage = 1; // 투사체 피해량

    void Start()
    {
        Destroy(gameObject, lifespan); // 설정된 시간 후 투사체 삭제
    }

    private void OnTriggerEnter2D(Collider2D collider)
    {
        if (collider.CompareTag("Player"))
        {
            // 플레이어에게 피해를 주는 로직 추가
            Debug.Log("Player hit by projectile!");
            // 피해 로직을 여기에 추가할 수 있습니다 (예: PlayerHealth 스크립트 참조)
            Destroy(gameObject); // 투사체 제거
        }
    }
}
