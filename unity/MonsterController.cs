using UnityEngine;

public class MonsterController : MonoBehaviour
{
    public float moveSpeed = 2f; // 이동 속도
    public float attackRange = 1f; // 근접 공격 범위
    public float projectileSpeed = 5f; // 투사체 속도
    public GameObject projectilePrefab; // 투사체 프리팹
    public Transform player; // 플레이어의 위치
    public float shootInterval = 2f; // 투사체 발사 간격
    public bool isMelee; // 근접 몬스터인지 여부
    private bool canShoot = true; // 투사체 발사 가능 여부
    private Vector3 startPosition; // 몬스터의 시작 위치
    private Vector3 targetPosition; // 몬스터의 목표 위치

    void Start()
    {
        startPosition = transform.position; // 시작 위치 저장
        targetPosition = startPosition; // 목표 위치 초기화
    }

    void Update()
    {
        Move();
        Attack();
    }

    void Move()
    {
        // 왔다 갔다 하는 로직
        float distance = Mathf.PingPong(Time.time * moveSpeed, 3f); // 이동 범위 설정
        transform.position = new Vector3(startPosition.x + distance, startPosition.y, startPosition.z);
    }

    void Attack()
    {
        if (isMelee)
        {
            // 근접 공격
            if (Vector2.Distance(transform.position, player.position) <= attackRange)
            {
                // 플레이어에게 피해를 주는 로직 추가
                Debug.Log("Player damaged by melee monster!");
            }
        }
        else
        {
            // 원거리 공격
            if (canShoot && Vector2.Distance(transform.position, player.position) <= 10f) // 사정거리 내에서만 발사
            {
                canShoot = false;
                Shoot();
                Invoke("ResetShoot", shootInterval); // 발사 간격 설정
            }
        }
    }

    void Shoot()
    {
        // 투사체 발사
        GameObject projectile = Instantiate(projectilePrefab, transform.position, Quaternion.identity);
        Vector2 direction = (player.position - transform.position).normalized;
        projectile.GetComponent<Rigidbody2D>().velocity = direction * projectileSpeed; // 투사체 속도 적용
    }

    void ResetShoot()
    {
        canShoot = true; // 투사체 발사 가능 상태로 초기화
    }
}
